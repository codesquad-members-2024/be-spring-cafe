package codesquad.springcafe.comment;

import java.util.List;

public interface CommentDatabase {

    void createComment(CommentCreateDTO comment);

    List<CommentShowDTO> getCommentList(Long articleId);

}
