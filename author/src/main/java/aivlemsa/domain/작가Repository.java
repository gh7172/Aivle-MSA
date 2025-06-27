package aivlemsa.domain;

import aivlemsa.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "작가", path = "작가")
public interface 작가Repository
    extends PagingAndSortingRepository<작가, Long> {}
