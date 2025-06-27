package aivlemsa.domain;

import aivlemsa.PurchaseApplication;
import aivlemsa.config.kafka.AbstractEvent;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Purchase_table")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private String bookId;

    @PostPersist
    public void onPostPersist() {
        // 이벤트 객체 생성 및 발행
        BookPurchaseRequested event = new BookPurchaseRequested();
        event.setId(this.getId());
        event.setUserId(this.getUserId());
        event.setBookId(this.getBookId());
        event.publishAfterCommit();
    }

    public static PurchaseRepository repository() {
        return PurchaseApplication.applicationContext.getBean(PurchaseRepository.class);
    }

    // 구매 상태 업데이트 커맨드 핸들링 메서드
    public static void updateState(PurchaseUpdatedCommand command) {
        repository().findById(command.getId()).ifPresent(purchase -> {
            // 예: 상태 필드가 있다면 여기에 업데이트 로직 추가
            // purchase.setState(command.getState());
            repository().save(purchase);
        });
    }
}
