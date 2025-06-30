package aivlemsa.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserLibraryRepository extends CrudRepository<UserLibrary, Long> {
    Optional<UserLibrary> findByUserIdAndBookId(Long userId, String bookId);
} 