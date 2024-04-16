package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    List<Post> findByWriter(String writer);
    List<Post> findAll();
}
