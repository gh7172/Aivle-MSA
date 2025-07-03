package aivlemsa.domain;

import aivlemsa.domain.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//<<< PoEAA / Repository
@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book b SET b.viewCount = b.viewCount + 1 WHERE b.bookId = :bookId")
    void incrementViewCount(@Param("bookId") Long bookId);
}