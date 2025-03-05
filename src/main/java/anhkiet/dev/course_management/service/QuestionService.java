package anhkiet.dev.course_management.service;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.Question;
import anhkiet.dev.course_management.repository.QuestionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question createNewQuestion(Question question){
        return this.questionRepository.save(question);
    }
}
