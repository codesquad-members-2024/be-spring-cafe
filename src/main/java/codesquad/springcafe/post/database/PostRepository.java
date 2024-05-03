package codesquad.springcafe.post.database;

import codesquad.springcafe.post.Post;
import codesquad.springcafe.user.User;

import java.util.List;

public interface PostRepository {

    List<Post> findAll();

    void save(String userId, Post post);

    Post findById(Long id);

    void updatePost(Post post, long id);

    void deletePost(Long id);
}
