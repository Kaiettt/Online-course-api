// package anhkiet.dev.course_management.service;

// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import anhkiet.dev.course_management.domain.entity.ConfirmationToken;
// import anhkiet.dev.course_management.repository.ConfirmationTokenRepository;

// @Service
// public class ConfirmationService {
//     private final ConfirmationTokenRepository confirmationTokenRepository;    

//     public ConfirmationService(ConfirmationTokenRepository confirmationTokenRepository){
//         this.confirmationTokenRepository = confirmationTokenRepository;
//     }

//     public Optional<ConfirmationToken>  getConfirmationByToken(String token){
//         return this.confirmationTokenRepository.findByToken(token);
//     }
// }
