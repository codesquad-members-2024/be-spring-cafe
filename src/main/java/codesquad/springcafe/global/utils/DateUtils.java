package codesquad.springcafe.global.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // LocalDateTime을 날짜 포맷 문자열로 변환하는 메서드
    public static String convertCreatedAt(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
