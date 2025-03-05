package anhkiet.dev.course_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Lesson;
import anhkiet.dev.course_management.domain.entity.Question;
import anhkiet.dev.course_management.domain.entity.Test;
import anhkiet.dev.course_management.domain.request.QuestionRequest;
import anhkiet.dev.course_management.domain.request.TestRequest;
import anhkiet.dev.course_management.repository.TestRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TestService {
    private final LessonService lessonService;
    private final TestRepository testRepository;
    private final QuestionService questionService;
    public Test createNewTest(TestRequest request) throws EntityExistsException{
        Lesson lesson = this.lessonService.getLessonById(request.getLessonId());
        if(lesson == null){
            throw new EntityExistsException("Lesson not found");
        }
        Test test = Test.builder()
                .attempsAllowed(request.getAttempsAllowed())
                .duration(request.getDuration())
                .lesson(lesson)
                .title(request.getTitle())
                .build();
        test = this.testRepository.save(test);
        if(this.getTestById(test.getId()) == null){
            throw new EntityExistsException("Test not found");
        }
        List<Question> questions = new ArrayList<>();
        for(QuestionRequest questionRequest: request.getQuestion()){
            Question question = Question.builder()
                .test(test)
                .questionTitle(questionRequest.getQuestionTitle())
                .answer(questionRequest.getAnswer())
                .questionOptions(questionRequest.getQuestionOptions())
                .build();
            question = this.questionService.createNewQuestion(question);
            questions.add(question);
        }
        test.setQuestions(questions);
        return this.testRepository.save(test);
    }
    public Test getTestById(long id){
        Optional<Test> test = this.testRepository.findById(id);
        if(!test.isPresent()){
            return null;
        }
        return test.get();
    }
    public Test getTestByID(long id) {
        Optional<Test> test = this.testRepository.findById(id);
        if(!test.isPresent()){
            return null;
        }
        return test.get();
    }
    
}
