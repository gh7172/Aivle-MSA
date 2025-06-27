package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class BookPurchaseRequested extends AbstractEvent {

    private String id;
    private Long userId;
    private String bookId;

    public BookPurchaseRequested(Purchase aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.bookId = aggregate.getBookId();
    }
}
