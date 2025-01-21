package anhkiet.dev.course_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import anhkiet.dev.course_management.domain.entity.ConfirmationToken;
import jakarta.transaction.Transactional;
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>, JpaSpecificationExecutor<ConfirmationToken>{
    Optional<ConfirmationToken> findByToken(String token);
    @Modifying
    @Transactional
    @Query(
        value = "UPDATE confirmation_token ct SET ct.confirmed_at = ?2 WHERE ct.token = ?1",
        nativeQuery = true
    )
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
