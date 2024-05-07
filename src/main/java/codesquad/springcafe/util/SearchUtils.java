package codesquad.springcafe.util;

public class SearchUtils {
    public static String formatForSqlLike(String keyword) {
        return "%" + keyword + "%";
    }
}
