package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    public void save(Post article);

    public Optional<Post> findById(Long postId);

    public List<Post> findAll();
}
