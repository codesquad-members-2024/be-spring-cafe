package codesquad.springcafe.global.repository;

import java.util.Collection;
import java.util.Optional;

public interface BasicRepository<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    Collection<T> findAll();

    void deleteAll();
}
