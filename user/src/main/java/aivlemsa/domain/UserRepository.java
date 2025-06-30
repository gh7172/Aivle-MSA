package aivlemsa.domain;

import aivlemsa.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "User", path = "User")
public interface UserRepository
    extends PagingAndSortingRepository<User, Long> {
        User findByLoginId(String loginId);
}
