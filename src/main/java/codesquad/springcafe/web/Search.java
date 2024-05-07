package codesquad.springcafe.web;

public class Search {
    public static final String TITLE_CONTENT_TYPE = "titleContent";
    public static final String WRITER_TYPE = "writer";
    private String searchType;
    private String keyword;

    public boolean isTitleContentType() {
        return this.searchType.equals(TITLE_CONTENT_TYPE);
    }

    public boolean isWriterType() {
        return this.searchType.equals(WRITER_TYPE);
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
