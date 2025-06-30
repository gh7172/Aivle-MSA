package aivlemsa.infra;

import aivlemsa.domain.UserLibrary;
import aivlemsa.domain.UserLibraryRepository;
import aivlemsa.domain.SubscriptionModel;
import aivlemsa.domain.SubscriptionModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/read")
@RequiredArgsConstructor
public class UserLibraryController {
    private final UserLibraryRepository userLibraryRepository;
    private final SubscriptionModelRepository subscriptionModelRepository;

    // 특정 유저가 특정 책을 열람할 수 있는지 확인 및 열람 요청 처리
    @GetMapping("/{userId}/{bookId}")
    public ResponseEntity<String> canReadBook(@PathVariable Long userId, @PathVariable String bookId) {
        Optional<UserLibrary> lib = userLibraryRepository.findByUserIdAndBookId(userId, bookId);
        if (lib.isPresent()) {
            return ResponseEntity.ok("열람 성공 : bookId=" + bookId);
        } else {
            Optional<SubscriptionModel> subscription = subscriptionModelRepository.findByUserId(userId);
            if (subscription.isPresent() && subscription.get().getExpirationDate().isAfter(LocalDateTime.now())) {
                return ResponseEntity.ok("열람 성공 : bookId=" + bookId);
            } else {
                return ResponseEntity.status(403).body("열람 실패: bookId=" + bookId);
            }
        }
    }
} 