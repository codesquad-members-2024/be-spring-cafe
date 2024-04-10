package codesquad.springcafe.global.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {
    Long save(T entity);

    Optional<T> findById(ID id);

    Collection<T> findAll();

    Integer countAll();
}
