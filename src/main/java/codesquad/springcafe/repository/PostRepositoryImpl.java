package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepository {

    List<Post> postDB = new ArrayList<>();

    @Override
    public void save(Post post) {
        post.setId((long) (postDB.size() + 1));
        postDB.add(post);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postDB.stream().filter(post -> post.getId().equals(postId)).findAny();
    }

    @Override
    public List<Post> findAll() {
        return postDB;
    }

    @Override
    public int findSize() {
        return postDB.size();
    }

    @Override
    public void update(Post post) {
    }

    @Override
    public void delete(String id) {
    }
}
