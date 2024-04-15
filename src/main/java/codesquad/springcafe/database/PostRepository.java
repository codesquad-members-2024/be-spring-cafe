package codesquad.springcafe.database;

import codesquad.springcafe.model.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private long nextId = 1;

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public void save(Post post) {
        if (post.getId() == null) {
            post.setId(nextId++);
        }
        if (post.getDateTime() == null) {
            post.setDateTime(LocalDateTime.now());
        }
        posts.add(post);
    }

    public Post findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
