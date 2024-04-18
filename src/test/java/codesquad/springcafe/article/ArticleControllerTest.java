package codesquad.springcafe.article;


import codesquad.springcafe.article.database.ArticleDatabase;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class ArticleControllerTest {

    @Mock
    private ArticleDatabase articleDatabase;
    @InjectMocks
    private ArticleController articleController;
    private MockMvc mockMvc;

}
