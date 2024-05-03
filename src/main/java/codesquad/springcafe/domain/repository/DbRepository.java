package codesquad.springcafe.domain.repository;

public interface DbRepository<T> {
    void add(T t);
}
