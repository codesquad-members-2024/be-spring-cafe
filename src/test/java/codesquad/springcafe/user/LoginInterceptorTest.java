package codesquad.springcafe.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoginInterceptorTest {

    @InjectMocks
    private LoginInterceptor loginInterceptor;
    @Mock
    private HttpSession httpSession;

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유저 세션이 존재할 때 preHandle 메서드는 true를 반환해야 함")
    void testPreHandleWithValidSession() throws Exception {
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("userId")).thenReturn("user");

        boolean result = loginInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        assertThat(result).isTrue();
        verify(httpServletResponse, never()).sendRedirect(anyString());

    }

    @Test
    @DisplayName("유저 세션이 존재하지 않을 때 preHandle 메서드는 false를 반환하고 리다이렉트해야 함")
    void testPreHandleWithInvalidSession() throws Exception {
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpServletRequest.getRequestURI()).thenReturn("/");
        when(httpSession.getAttribute("userId")).thenReturn(null);

        boolean result = loginInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        assertThat(result).isFalse();
        assertThat(httpServletRequest.getRequestURI()).isEqualTo("/");
        verify(httpServletResponse, times(1)).sendRedirect("/user/login?return_to=/");
    }
}