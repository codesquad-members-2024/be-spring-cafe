package codesquad.springcafe.util;

public enum Sort {
    ASCENDING("asc"),
    DESCENDING("desc");

    public final String val;

    Sort(String val) {
        this.val = val;
    }

    public static Sort sorted() {
        return Sort.ASCENDING;
    }

    public static Sort unSorted() {
        return Sort.DESCENDING;
    }
}
