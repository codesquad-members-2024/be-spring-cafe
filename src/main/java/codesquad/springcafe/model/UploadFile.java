package codesquad.springcafe.model;

public class UploadFile {
    private Long id;
    private final Long articleId;
    private final String uploadFileName;
    private final String storeFileName;

    public UploadFile(Long articleId, String uploadFileName, String storeFileName) {
        this.articleId = articleId;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public String getStoreFileName() {
        return storeFileName;
    }
}
