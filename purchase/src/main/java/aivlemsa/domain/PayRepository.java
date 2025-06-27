package aivlemsa.domain;

import aivlemsa.domain.*;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "pays", path = "pays")
public interface PayRepository extends PagingAndSortingRepository<Pay, String> {
    @Query(
        value = "select pay " +
        "from Pay pay " +
        "where(:id is null or pay.id like %:id%) and (:userId is null or pay.userId = :userId) and (:bookId is null or pay.bookId like %:bookId%)"
    )
    Pay getPay(String id, Long userId, String bookId);
}
