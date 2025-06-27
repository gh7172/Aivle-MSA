package aivlemsa.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "generate", path = "generate")
public interface GenerateRepository
    extends PagingAndSortingRepository<Generate, Long> {}
