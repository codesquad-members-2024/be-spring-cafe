package codesquad.springcafe.service;

import codesquad.springcafe.database.PostRepository;
import codesquad.springcafe.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post findByPostId(long postId){
        return postRepository.findById(postId);
    }

    public void createPost(Post post){
        postRepository.save(post);
    }
}
