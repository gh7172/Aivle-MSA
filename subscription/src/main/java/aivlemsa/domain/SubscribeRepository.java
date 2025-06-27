package aivlemsa.domain;

import aivlemsa.domain.*;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "subscribes",
    path = "subscribes"
)
public interface SubscribeRepository
    extends PagingAndSortingRepository<Subscribe, Long> {
    @Query(
        value = "select subscribe " +
        "from Subscribe subscribe " +
        "where(:userId is null or subscribe.userId = :userId) and (:subscriptionExpiryDate is null or subscribe.subscriptionExpiryDate = :subscriptionExpiryDate)"
    )
    Subscribe getSubscribe(Long userId, Date subscriptionExpiryDate);
}
