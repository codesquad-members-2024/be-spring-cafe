# be-spring-cafe
2024 마스터즈 백엔드 스프링 카페

## 🔒 규칙 설정
- 도메인 패키지 구조 적용
    - **도메인을 기준으로 패키지를 나눈 구조**
    - 예를 들어 회원에 관련된 기능은 user 패키지에 포함시킨다

- 브랜치 전략
    - 브랜치는 main - sharpie1330 - feature로 구성
    - 브랜치명은 `feature{feature-number}`

- 커밋 컨벤션
    ```
    * prefix *
    FEAT: 새로운 기능의 추가
    REMOVE: 파일, 코드 삭제 또는 기능 삭제
    FIX: 버그 수정
    DOCS: 문서 수정
    STYLE: 스타일 관련 수정 (코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
    REFACTOR: 코드 리팩토링
    TEST: 테스트 관련 코드
    CHORE: 빌드 관련 수정 (application.yml, build.gradle, .gitignore ..)
    COMMENT: 필요한 주석 추가 및 변경
    RENAME: 파일 혹은 폴더명 수정하거나 옮기는 작업만인 경우
    ```