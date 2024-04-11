package codesquad.springcafe.Service;

import codesquad.springcafe.Domain.Post;
import codesquad.springcafe.Repository.PostRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository; //구현체 주입
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post findPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }
}
