package codesquad.springcafe.domain.repository;

public interface DbRepository<T> {
    T add(T t);
}
