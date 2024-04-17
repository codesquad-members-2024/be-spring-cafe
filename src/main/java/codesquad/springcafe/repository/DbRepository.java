package codesquad.springcafe.repository;

import java.util.List;

public interface DbRepository<T> {
    void add(T t);

    List<T> getAll();
}
