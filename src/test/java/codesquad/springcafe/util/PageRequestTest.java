package codesquad.springcafe.util;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageRequestTest {

    @DisplayName("현재 페이지는 10, 사이즈는 15, 오름차순 정렬 조건으로 생성할 수 있다")
    @Test
    void create_success() {
        // given
        int page = 10;
        int size = 15;
        Sort sort = Sort.sorted();

        // when
        PageRequest pageRequest = new PageRequest(page, size, sort);

        // then
        assertThat(pageRequest.getPageNumber()).isEqualTo(10);
        assertThat(pageRequest.getPageSize()).isEqualTo(15);
        assertThat(pageRequest.getSort()).isEqualTo(Sort.ASCENDING);
    }

    @DisplayName("현재 페이지가 0보다 작으면 ResourceNotFoundException 예외를 던진다")
    @Test
    void create_fail_when_page_is_under_zero() {
        // given
        int page = -1;
        int size = 15;
        Sort sort = Sort.sorted();

        // when & then
        assertThatThrownBy(() -> new PageRequest(page, size, sort))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("요청하는 컨텐츠 사이즈가 1보다 작으면 IllegalArgumentException 예외를 던진다")
    @Test
    void create_fail_when_size_is_under_zero() {
        // given
        int page = 10;
        int size = 0;
        Sort sort = Sort.sorted();

        // when & then
        assertThatThrownBy(() -> new PageRequest(page, size, sort))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("현재 페이지가 10번째고, 요청 사이즈가 15면 offset은 150이다")
    @Test
    void getOffset() {
        // given
        int page = 10;
        int size = 15;
        Sort sort = Sort.sorted();
        PageRequest pageRequest = new PageRequest(page, size, sort);

        // when
        long offset = pageRequest.getOffset();

        // then
        assertThat(offset).isEqualTo(150L);
    }

    @DisplayName("현재 요청 페이지가 10 이고 요청 사이즈가 15 일 때 다음 요청 페이지는 11 이면서 요청 사이즈는 그대로 15이다")
    @Test
    void next() {
        // given
        int page = 10;
        int size = 15;
        Sort sort = Sort.sorted();
        PageRequest pageRequest = new PageRequest(page, size, sort);

        // when
        PageRequest next = pageRequest.next();

        // then
        assertThat(next.getPageNumber()).isEqualTo(11);
        assertThat(next.getPageSize()).isEqualTo(15);
    }

    @DisplayName("현재 요청 페이지가 10 이고 요청 사이즈가 15 일 때 이전 요청 페이지는 9 이면서 요청 사이즈는 그대로 15이다")
    @Test
    void previous() {
        // given
        int page = 10;
        int size = 15;
        Sort sort = Sort.sorted();
        PageRequest pageRequest = new PageRequest(page, size, sort);

        // when
        PageRequest previous = pageRequest.previous();

        // then
        assertThat(previous.getPageNumber()).isEqualTo(9);
        assertThat(previous.getPageSize()).isEqualTo(15);
    }
}