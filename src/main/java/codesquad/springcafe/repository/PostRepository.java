package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    Optional<Post> findById(Long postId);

    List<Post> findAll();

    int findSize();

    void update(Post post);

    void delete(String id);
}
