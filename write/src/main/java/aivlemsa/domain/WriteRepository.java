package aivlemsa.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//<<< PoEAA / Repository
//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "write", path = "write")
public interface WriteRepository extends PagingAndSortingRepository<Write, Long> {

    // 사용자별 조회
    List<Write> findByUserId(@Param("userId") Long userId);

    // 상태별 조회
    List<Write> findByState(@Param("state") String state);
}