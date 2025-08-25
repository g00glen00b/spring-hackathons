package codes.dimitri.apps.leaderboard.shared;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID> extends Repository<T, ID> {
    List<T> findAll();

    List<T> findAllById(Iterable<ID> ids);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    long count();

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);
}
