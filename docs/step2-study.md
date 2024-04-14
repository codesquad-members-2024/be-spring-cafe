# step2
> 스프링 카페 - 글 쓰기 기능 구현
- 기능을 구현하며 공부했던 내용들을 정리합니다
---

## 비밀번호 암호화
글 쓰기 기능을 구현하기 전에 로그인한 사용자만 글 쓰기가 가능하도록 로그인 기능을 구현하면서, 회원가입 기능때부터 필요하다고 생각했던 비밀번호 암호화 기능을 구현하고자 했다.
 
### 단방향 암호화(One-Way Encryption)
- 한 쪽 방향으로 암호화를 한다는 의미
- 암호화만 가능하고 복호화는 할 수 없다. 따라서 비밀번호 관리에 유용하다. DB에 그대로 저장하는 경우 유출되어도 안전
- 검증 시 사용자로부터 입력받은 비밀번호를 똑같은 방식으로 암호화해 암호화된 패스워드끼리 비교하면 된다
- 비밀번호 잊어버렸을 때 찾기 불가(재설정으로 해결한다)
- 대표적으로 SHA-256 암호화 알고리즘 많이 사용

### 단방향 해시 함수의 한계점
- Rainbow Table
  - 동일한 메시지는 동일한 다이제스트(해시 함수라는 수학적 연산을 통해 생성된 암호화된 메시지)를 갖는다
- 이 테이블은 해시 함수를 사용해 변환 가능한 모든 해시 값을 저장해 놓은 표.
- 사용자들이 많이 사용하는 password나 복잡하지 않은 암호의 경우 이미 공격자들이 대입해보았을 확률이 높다
- Salting
  - PWD에 임의의 문자열인 Salt를 추가해 다이제스트를 생성
  - 같은 패스워드라도 각기 다른 Salt를 추가해 다이제스트가 다르게 생성되어 Rainbow table을 무의미하게 만든다
  - 최소 128bit는 되어야 안전!

### 해시 알고리즘
- 해싱? : 해시 함수를 사용해 특정 메시지 또는 문자열에서 해시를 생성하는 프로세스
- 비밀번호 해싱에 특화된 해시 함수는 보안을 위해 다음 네 가지 주요 속성을 갖춰야
  - deterministic: 동일한 해시 함수로 처리된 동일한 메시지는 항상 동일한 해시 생성
  - not reversible: 해시에서 메시지를 생성하는 것은 비실용적
  - high entropy: 메시지를 조금만 변경해도 완전히 다른 해시를 생성해야
  - resist collisions: 서로 다른 두 메시지가 동일한 해시 생성하면 안됨
- 또한 비밀번호 해싱 함수는 느려야 한다. 빠른 알고리즘은 brute force 알고리즘에 취약

#### 권장되지 않는 암호화 알고리즘
- MD5 : 계산적으로 충돌 일으키기 쉽다. 빠른 알고리즘. Brute Force에 취약
- SHA-512 : salt와 함께 사용하는 경우 여전히 좋은 선택지이지만, 더 강력하고 느린 선택지도 있다

#### 권장되는 알고리즘
- PBKDF2, BCrypt, SCrypt
- 이들은 각각 느리지만 강도를 설정할 수 있다는 뛰어난 특징 가짐
- 컴퓨터 성능이 향상되면 입력을 변경해 알고리즘의 속도를 늦출 수 있다
- BCrypt나 SCrypt는 아직 Java와 함께 제공되지 않고, 별도의 라이브러리(Spring Security)를 사용해야 함
- 또한 이런 알고리즘은 내부적으로 salt 생성. 추후 비밀번호 유효성 검사에 사용할 수 있도록 해시 내에 솔트 저장
  - BCrypt의 salt와 관련된 내용 참고: https://jhkimmm.tistory.com/24

### 프로젝트에 도입해보기
```
public String encode(CharSequence rawPassword) {
    byte[] salt = generateSalt();
    byte[] encoded = encode(rawPassword, salt);
    return Base64.getEncoder().encodeToString(encoded);
}
```
salt를 생성하고, 내부적으로 구현된 encode 메서드로 인코딩한 뒤, Base64로 인코딩해 반환

- 내부적으로 구현된 encode 메서드가 어떻게 구현되었는지 살펴보면...
```
    private byte[] encode(CharSequence rawPassword, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(),
                    concatenate(salt, SECRET_KEY.getBytes(StandardCharsets.UTF_8)),
                    ITERATION, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return concatenate(salt, hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("해싱이 불가능합니다.", e);
        }
    }
```
- 사용된 클래스 설명
  - KeySpec은 단순히 암호화와 관련된 여러 구현제들을 가지는 인터페이스
  - PBEKeySpec은 패스워트 기반 암호화를 위한 구현 클래스로, 암호화에 사용되는 패스워드와 관련된 파라미터들을 포함한다
  - SecretKeyFactory는 시크릿 키를 생성하기 위한 팩토리 클래스로, 여러 알고리즘을 지원한다

- 구현 내용
  - 위 코드에서는 정의한 KeySpec에 따라 시크릿 키 객체를 생성하고, 해당 객체를 주요 인코딩 형식으로 반환한다
  - Spec에 정의한 내용을 살펴보면, 평문 비밀번호와, 시크릿 키와 salt를 합친 바이트 배열을 가지고 해시 함수를 ITERATION 횟수만큼 반복한다. 길이도 함께 정의한다
  - 해시 함수로는 Hmac-Sha256을 사용했다 (이에 대해 더 자세한 정보는 https://sunphiz.me/wp/archives/1104)
  - salt 값은 랜덤으로 생성되기 때문에 별도로 저장되어야 암호 매칭이 가능. 따라서 합쳐 반환한다

---
## 참고 링크
- https://www.baeldung.com/java-password-hashing
- https://hyunseo-fullstackdiary.tistory.com/127
- https://blog.jiniworld.me/172