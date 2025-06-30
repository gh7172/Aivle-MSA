package aivlemsa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GetPayRepository extends JpaRepository<GetPay, String> {
    // 필요하면 추가 메서드 정의 가능
    // 예: Optional<GetPay> findByUserId(Long userId);
}
