package codesquad.springcafe.global.model;

import java.time.LocalDateTime;

/**
 * 모든 model 클래스에 생성시간, 수정시간 (createAt, modifiedAt) 추가하기 위한 부모 클래스 역할
 */
public class BaseTime {
    private final LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public BaseTime(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
