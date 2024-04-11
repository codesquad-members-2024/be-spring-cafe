package codesquad.springcafe.Repository;

import static org.assertj.core.api.Assertions.*;
;
import codesquad.springcafe.Domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostRepositoryImplTest {
    private PostRepository postRepository;
    private Post post;
    @BeforeEach
    public void beforeEach() {
        postRepository = new PostRepositoryImpl();

        post = new Post();
        post.setTitle("testTitle");
        post.setContent("hello,world!");
    }
    @Test
    @DisplayName("save가 잘 되는지 확인!")
    void save() {
        postRepository.save(post);
        boolean isPost = postRepository.findById(1L).isPresent();
        assertThat(isPost).isTrue();

        Post post2 = new Post();
        post2.setTitle("testTitle2");
        post2.setContent("hello,java!");
        postRepository.save(post2);
        boolean isPost2 = postRepository.findById(2L).isPresent();
        assertThat(isPost2).isTrue();
    }

    @Test
    @DisplayName("findById로 post가 잘 찾아와 지는지 확인")
    void findById() {
        postRepository.save(post);
        Post post = postRepository.findById(1L).get();
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("testTitle");
    }
}
