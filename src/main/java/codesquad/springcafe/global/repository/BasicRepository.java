package codesquad.springcafe.global.repository;

import java.util.Optional;

public interface BasicRepository<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    void softDeleteById(ID id, T deletedEntity);

    void update(ID id, T updatedEntity);
}
