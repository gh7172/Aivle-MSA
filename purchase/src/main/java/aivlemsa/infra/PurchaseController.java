package aivlemsa.infra;

import aivlemsa.domain.GetPay;
import aivlemsa.infra.GetPayQuery;
import aivlemsa.domain.GetPayRepository;
import aivlemsa.infra.GetPayQueryService;
import aivlemsa.domain.BookPurchaseCommand;
import aivlemsa.domain.PurchaseUpdatedCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final GetPayRepository getPayRepository;
    private final GetPayQueryService getPayQueryService;

    // 도서 구매 요청
    @PostMapping
    public ResponseEntity<GetPay> requestBookPurchase(@RequestBody BookPurchaseCommand command) {
        GetPay purchase = new GetPay();
        purchase.setUserId(command.getUserId());
        purchase.setBookId(String.valueOf(command.getBookId())); // or convert as needed
        purchase.setStatus("결제 요청됨");

        GetPay saved = getPayRepository.save(purchase);
        return ResponseEntity.ok(saved);
    }

    // 구매 상태 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetPayQuery> getPurchaseStatus(@PathVariable String id) {
        Optional<GetPayQuery> result = getPayQueryService.getPayById(id);
        return result.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 구매 상태 업데이트 (예: 결제 성공/실패 처리)
    @PutMapping("/update")
    public ResponseEntity<GetPay> updatePurchaseStatus(@RequestBody PurchaseUpdatedCommand command) {
        return getPayRepository.findById(command.getId())
                .map(pay -> {
                    pay.setStatus(command.getStatus());
                    GetPay updated = getPayRepository.save(pay);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}