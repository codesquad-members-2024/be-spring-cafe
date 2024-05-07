package codesquad.springcafe.comment;

import java.util.List;

public interface CommentDatabase {

    void createComment(CommentCreateDTO comment);

    String getCommentWriter(Long commentId);

    List<CommentShowDTO> getCommentList(Long articleId);

    boolean hasOtherComment(Long articleId, String writer);

    void deleteComment(Long commentId);

    void editComment(CommentEditDTO comment);

}
