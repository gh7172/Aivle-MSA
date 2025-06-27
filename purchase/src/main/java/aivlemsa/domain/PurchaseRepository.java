package aivlemsa.domain;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    // 필요 시 커스텀 쿼리 메서드 추가 가능
}
