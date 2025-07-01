package aivlemsa.domain;

import aivlemsa.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "Author", path = "Author")
public interface AuthorRepository
    extends PagingAndSortingRepository<Author, Long> {}
