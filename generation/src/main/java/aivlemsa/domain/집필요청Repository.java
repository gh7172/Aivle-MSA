package aivlemsa.domain;

import aivlemsa.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "집필요청", path = "집필요청")
public interface 집필요청Repository
    extends PagingAndSortingRepository<집필요청, Long> {}
