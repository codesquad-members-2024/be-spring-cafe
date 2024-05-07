package codesquad.springcafe.util;

import java.util.List;

public class PaginationHelper<T> {
    private final List<T> items;
    private final int pageSize;

    public PaginationHelper(List<T> items, int pageSize) {
        this.items = items;
        this.pageSize = pageSize;
    }

    public List<T> getPage(Long pageNumber) {
        long startIndex = (pageNumber - 1) * pageSize;
        if (startIndex >= items.size()) {
            return List.of(); // 페이지 범위를 넘어간 경우 빈 리스트 반환
        }
        long endIndex = Math.min(startIndex + pageSize, items.size());
        return items.subList((int) startIndex, (int) endIndex);
    }

    public boolean hasMorePages(Long pageNumber) {
        long startIndex = (pageNumber - 1) * pageSize;
        return startIndex + pageSize < items.size();
    }
}