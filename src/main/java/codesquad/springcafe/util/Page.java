package codesquad.springcafe.util;

import java.util.List;
import java.util.stream.LongStream;

public class Page {
    private final PageRequest pageRequest;
    private final Long totalLastPage;
    private final Long firstPage;
    private final Long lastPage;


    public Page(PageRequest pageRequest, Long totalCount) {
        int pageSize = pageRequest.getSize();
        int buttonCount = pageRequest.getButtonCount();
        Long originalLastPage = (long) (Math.ceil(pageRequest.getNumber()*1.0/buttonCount))*buttonCount;
        this.pageRequest = pageRequest;
        this.totalLastPage = (long) (Math.ceil(totalCount*1.0/pageSize));
        this.lastPage = Math.min(totalLastPage, originalLastPage);
        this.firstPage = originalLastPage - 4;
    }

    public Long getNumber() {
        return pageRequest.getNumber();
    }

    public Long getTotalLastPage() {
        return totalLastPage;
    }

    public Long getStartPage() {
        return firstPage;
    }

    public Long getLastPage() {
        return lastPage;
    }

    public Long getPrevPage() {
        return firstPage - 1;
    }

    public Long getNextPage() {
        return lastPage + 1;
    }

    public boolean hasPrevButton() {
        return firstPage > 1;
    }

    public boolean hasNextButton() {
        return lastPage < totalLastPage;
    }

    public List<Long> getMiddleNumbers() {
        return LongStream.rangeClosed(firstPage, lastPage).boxed().toList();
    }
}
