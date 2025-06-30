package aivlemsa.domain;

import aivlemsa.ReadApplication;
import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import java.util.Optional;

@Entity
@Table(name = "UserLibrary")
@Data
public class UserLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private String bookId;
    private LocalDateTime expirationDate;
    private LocalDateTime acquiredDate;

    public static void updateLibrary(PurchasePaymentSucceeded event) {
        UserLibraryRepository repo = ReadApplication.applicationContext.getBean(UserLibraryRepository.class);
        Optional<UserLibrary> lib = repo.findByUserIdAndBookId(event.getUserId(), event.getBookId());
        UserLibrary entity = lib.orElse(new UserLibrary());
        entity.setUserId(event.getUserId());
        entity.setBookId(event.getBookId());
        entity.setExpirationDate(LocalDateTime.MAX);
        entity.setAcquiredDate(LocalDateTime.now());
        repo.save(entity);
    }
} 