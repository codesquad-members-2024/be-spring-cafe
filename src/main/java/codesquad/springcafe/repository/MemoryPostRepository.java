package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Post;
import codesquad.springcafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryPostRepository implements PostRepository {
    private static final List<Post> posts = new ArrayList<>();

    @Override
    public Post save(Post post) {
        posts.add(post);
        return post;
    }

    @Override
    public List<Post> findByWriter(String writer) {
        return posts.stream().filter(post -> post.getWriter().equals(writer)).toList();
    }

    @Override
    public List<Post> findAll() {
        return posts;
    }
}
