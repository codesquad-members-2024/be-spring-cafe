package codesquad.springcafe.post.database;

import codesquad.springcafe.post.Post;

import java.util.List;

public interface PostRepository {

    List<Post> findAll();

    void save(Post post);

    Post findById(Long id);
}
