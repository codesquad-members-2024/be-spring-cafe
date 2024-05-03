package codesquad.springcafe.domain.comment.data;

import java.util.List;

public class CommentListResponse {
    private final Integer totalCommentCnt;
    private final List<CommentResponse> comments;

    public CommentListResponse(List<CommentResponse> comments) {
        this.totalCommentCnt = comments.size();
        this.comments = comments;
    }

    public Integer getTotalCommentCnt() {
        return totalCommentCnt;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }
}
