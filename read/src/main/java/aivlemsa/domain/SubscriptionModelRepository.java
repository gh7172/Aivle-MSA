package aivlemsa.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface SubscriptionModelRepository extends CrudRepository<SubscriptionModel, Long> {
    Optional<SubscriptionModel> findByUserId(Long userId);
} 