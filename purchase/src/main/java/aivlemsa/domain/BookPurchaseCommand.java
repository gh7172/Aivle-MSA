package aivlemsa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 도서 구매 요청 커맨드 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookPurchaseCommand implements Serializable {
    private Long userId;
    private Long bookId;
}
