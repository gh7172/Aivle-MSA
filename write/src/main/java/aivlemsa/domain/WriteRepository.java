package aivlemsa.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "write", path = "write")
public interface WriteRepository
    extends PagingAndSortingRepository<Write, Long> {}
