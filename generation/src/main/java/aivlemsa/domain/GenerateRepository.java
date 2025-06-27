package aivlemsa.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "집필요청", path = "집필요청")
public interface GenerateRepository
    extends PagingAndSortingRepository<Generate, Long> {}
