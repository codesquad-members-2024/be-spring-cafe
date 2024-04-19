package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryPostRepository implements PostRepository {
    private static final List<Post> posts = new ArrayList<>();

    @Override
    public Post save(Post post) {
        post.setId(posts.size()+1);
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

    @Override
    public Post findById(int id) {
        return posts.get(id-1);
    }
}
