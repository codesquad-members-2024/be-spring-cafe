package codesquad.springcafe.post.database;

import codesquad.springcafe.post.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostMemoryRepository implements PostRepository {

    private final List<Post> posts = new ArrayList<>();
    private long nextId = 1;

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    @Override
    public void save(Post post) {
        if (post.getId() == null) {
            post.setId(nextId++);
        }
        if (post.getDateTime() == null) {
            post.setDateTime(LocalDateTime.now());
        }
        posts.add(post);
    }

    @Override
    public Post findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findAny()
                .orElse(null);
    }
}
