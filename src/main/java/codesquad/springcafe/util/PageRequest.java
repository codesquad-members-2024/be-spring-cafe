package codesquad.springcafe.util;

import codesquad.springcafe.service.exception.ResourceNotFoundException;

public class PageRequest {

    private final int page;
    private final int size;
    private final Sort sort;

    public static PageRequest of(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }

    public PageRequest(int page, int size) {
        this(page, size, Sort.sorted());
    }

    public PageRequest(int page, int size, Sort sort) {
        if (page < 0) {
            throw new ResourceNotFoundException("페이지 인덱스는 반드시 0보다 작을 수 없습니다.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("페이지 사이즈는 1보다 작을 수 없습니다.");
        }

        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public int getPageNumber() {
        return page;
    }

    public int getPageSize() {
        return size;
    }

    public long getOffset() {
        return (long) page * (long) size;
    }

    public Sort getSort() {
        return sort;
    }

    public PageRequest next() {
        return new PageRequest(page + 1, size, sort);
    }

    public PageRequest previous() {
        return hasPrevious() ? new PageRequest(page - 1, size, sort) : this;
    }

    public PageRequest first() {
        return new PageRequest(0, size, sort);
    }

    public boolean hasPrevious() {
        return page > 0;
    }
}
