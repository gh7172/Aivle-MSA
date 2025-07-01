package aivlemsa.domain;

import javax.persistence.PrePersist;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookPurchaseRequested extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long bookId;
    private Integer price;

    public BookPurchaseRequested(Purchase aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.bookId = aggregate.getBookId();
        this.price = aggregate.getPrice(); 
    }

    public BookPurchaseRequested() {
        super();
    }
}

