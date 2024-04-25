package codesquad.springcafe.post;

import codesquad.springcafe.error.exception.ResourceNotFoundException;
import codesquad.springcafe.post.database.PostRepository;
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
        Post post = postRepository.findById(postId);
        if(post == null)
            throw new ResourceNotFoundException("게시글을 찾을수 없습니다." + postId);
        return postRepository.findById(postId);
    }

    public void createPost(String userId, Post post){
        postRepository.save(userId, post);
    }
    public void updatePost(long postId, Post post){
        postRepository.updatePost(post,postId);
    }

    public void deletePost(long postId){
        postRepository.deletePost(postId);
    }
}
