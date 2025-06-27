package aivlemsa.controller;

import aivlemsa.domain.BookPurchaseCommand;
import aivlemsa.domain.PurchaseUpdatedCommand;
import aivlemsa.domain.Purchase;
import aivlemsa.domain.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;

    /**
     * 도서 구매 요청
     */
    @PostMapping
    public ResponseEntity<Purchase> purchaseBook(@RequestBody BookPurchaseCommand command) {
        Purchase purchase = new Purchase();
        purchase.setUserId(command.getUserId());
        purchase.setBookId(command.getBookId());
        // 기본 상태 설정
        purchase.setState("REQUESTED");

        Purchase saved = purchaseRepository.save(purchase);
        return ResponseEntity.ok(saved);
    }

    /**
     * 구매 업데이트 요청
     */
    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(
            @PathVariable String id,
            @RequestBody PurchaseUpdatedCommand command) {
        return purchaseRepository.findById(id)
                .map(purchase -> {
                    // 상태 변경 등 필요한 비즈니스 로직
                    purchase.setUserId(command.getUserId());
                    purchase.setBookId(command.getBookId());
                    purchase.setState("UPDATED");
                    return ResponseEntity.ok(purchaseRepository.save(purchase));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
