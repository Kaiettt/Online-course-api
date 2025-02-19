package anhkiet.dev.course_management.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.ConfirmationToken;
import anhkiet.dev.course_management.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationService {
    private final ConfirmationTokenRepository confirmationTokenRepository;    

    public ConfirmationService(ConfirmationTokenRepository confirmationTokenRepository){
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public Optional<ConfirmationToken> getConfirmationByTokenAndEmail(long token,String email){
        return this.confirmationTokenRepository.findByTokenAndUserEmail(token,email);
    }

    public void ConfirmToken(long tokenNumber,String email) {
        this.confirmationTokenRepository.updateConfirmedAt(tokenNumber, LocalDateTime.now(),email);
    }
}
