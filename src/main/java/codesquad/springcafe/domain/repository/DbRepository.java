package codesquad.springcafe.domain.repository;

import java.util.List;

public interface DbRepository<T> {
    void add(T t);

    List<T> getAll();
}
