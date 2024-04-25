package codesquad.springcafe.web;

public class PageGroup {
    public static final int pageSize = 5;
    private final long totalPages;
    private final long currentPage;
    private final long startPage;
    private final long endPage;

    public PageGroup(long currentPage, long totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.startPage = calculateStartPage(currentPage);
        this.endPage = calculateEndPage(totalPages);


    }

    private long calculateStartPage(long currentPage) {
        return ((currentPage - 1) / pageSize) * pageSize + 1;
    }

    private long calculateEndPage(long totalPages) {
        if (totalPages == 0) {
            return 1;
        }
        return Math.min(startPage + pageSize - 1, totalPages);
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public long getStartPage() {
        return startPage;
    }

    public long getEndPage() {
        return endPage;
    }
}
