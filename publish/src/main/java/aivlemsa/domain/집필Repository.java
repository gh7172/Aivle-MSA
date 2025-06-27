package aivlemsa.domain;

import aivlemsa.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "집필", path = "집필")
public interface 집필Repository
    extends PagingAndSortingRepository<집필, Long> {}
