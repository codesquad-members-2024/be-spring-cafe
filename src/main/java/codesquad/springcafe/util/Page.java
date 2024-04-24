package codesquad.springcafe.util;

import java.util.Iterator;
import java.util.List;

public class Page<T> {
    private final List<T> content;
    private final PageRequest pageRequest;
    private final long total;

    public Page(List<T> content, PageRequest pageRequest, long total) {
        this.content = content;
        this.pageRequest = pageRequest;
        this.total = total;
    }

    public int getTotalPages() {
        return getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getPageSize());
    }

    public long getTotalElements() {
        return total;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPageNumber() {
        return pageRequest.getPageNumber();
    }

    public int getPageSize() {
        return pageRequest.getPageSize();
    }

    public int getNumberOfElements() {
        return content.size();
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public boolean isFirst() {
        return pageRequest.getPageNumber() == 0;
    }

    public boolean isLast() {
        return !hasNext();
    }

    public boolean hasNext() {
        return pageRequest.getPageNumber() + 1 < getTotalPages();
    }

    public boolean hasPrevious() {
        return pageRequest.getPageNumber() > 0;
    }

    public PageRequest nextPage() {
        return hasNext() ? pageRequest.next() : pageRequest;
    }

    public PageRequest previousPage() {
        return hasPrevious() ? pageRequest.previous() : pageRequest;
    }

    public Sort getSort() {
        return pageRequest.getSort();
    }

    public Iterator<T> iterator() {
        return content.iterator();
    }
}
