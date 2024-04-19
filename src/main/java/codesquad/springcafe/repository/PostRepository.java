package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    List<Post> findByWriter(String writer);
    List<Post> findAll();
    public Post findById(int id);
}
