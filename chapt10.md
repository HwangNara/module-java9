# Chapt10. 자바 9를 위한 코드 준비하기

## 다루는 내용
- 레거시 코드와 자겅ㅂ하고 자바 9에서 실행할 준비하기
- 자바 9에서 레거시 코드를 컴파일하고 자바 9 런타임에서 자바 9 하위 버전으로 컴파일된 코드 실행하기
- 자바 9의 클래스패스 동작과 이름 없는 모듈(unnamed module)
- 오류와 비표준 API 접근을 다루고 jdeps 도구 사용하기
- 오버라이드 스위치(override switch)를 사용하여 까다로운 코드와 API 우회하기

## 10.1. 자바 9 마이그레이션 시작
마이그레이션 작업량 두가지 요소
1. 마이그레이션의 성격
    1. 기존 코드를 자바 9 환경에서 예전처럼 컴파일하고 실행하기
    2. 코드 구졸르 리팩토링하여 모듈 기능 적용하기
2. 코드가 작성된 방식

## 10.2. 자바 8 기반의 응용 프로그램 예제 (shopping bag 유틸리티)
**ShoppingBag.java**
```java
package chapt10;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

public class ShoppingBag {

    public static String END_TOKEN = "end";
    private Bag<String> bag = new HashBag<>();

    public boolean addToBag(String itemName) {
        return (END_TOKEN.equals(itemName)) || this.bag.add(itemName);
    }

    public void prettyPrintBag() {
        System.out.println();
        System.out.println("Shopping Bag Contents");
        System.out.println("-----------------------");
        this.bag.uniqueSet()
            .forEach(item -> {
                System.out.println(item + " " + bag.getCount(item));
                System.out.println("------------------------");
            });
    }
}
```

**UserInputUtil.java**
```java
package chapt10;

import java.util.Scanner;

public class UserInputUtil {

    Scanner scanner = new Scanner(System.in);

    public String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
```

**Application.java**
```java
package chapt10;

import java.util.logging.Logger;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        logger.info("Shopping Bag application: Started");

        ShoppingBag shoppingBag = new ShoppingBag();
        UserInputUtil userInputUtil = new UserInputUtil();
        String itemName;
        do {
            itemName = userInputUtil.getUserInput("Enter item (Type '" + ShoppingBag.END_TOKEN + "' when done): " );
            shoppingBag.addToBag(itemName);
        } while (!ShoppingBag.END_TOKEN.equals(itemName));
        userInputUtil.close();
        shoppingBag.prettyPrintBag();
        logger.info("Shopping Bag application: Completed");
    }
}
```

추가 라이브러리
- commons-collections4-4.1.jar

## 10.3. 자바 9 컴파일러와 런타임 사용하기
컴파일
 
 ` javac -cp src/lib/commons-collections4-4.1.jar -d out  $(find . -name '*.java') `
 
실행
 
 ` java -cp out;src/lib/commons-collections4-4.1.jar com.packt.sortstrings.app.Application ` (Windows)
 ` java -cp out:src/lib/commons-collections4-4.1.jar com.packt.sortstrings.app.Application ` (Mac or Linux)

이름 없는 모듈(unnamed module)
- 자바9에서 레거시 코드를 실행하게 하는 과정을 지원하고자 도입된 기능 

### 10.3.1 이름 없는 모듈
- 자바 9에서 -cp 옵션으로 컴파일하거나 실행하면 플랫폼은 이름 없는 모듈을 자동으로 생성합니다. 클래스패스에 있는 모든 클래스와 JAR 파일은 이름 없는 모듈로 배포됩니다.
- 이름 없는 모듈은 자동으로 모든 관찰 대상 모듈을 읽습니다. 따라서 모든 자바 9 플랫폼 모듈뿐만 아니라 모듈 경로에 있는 다른 응용 프로그램과 라이브러리 모듈도 읽슷ㅂ니다(모듈 경로를 추가하면 이것도 클래스패스에 포함됩니다).

### 10.3.2 비표준 접근 다루기
#### jdeps (Java Dependency Analysis Tool)

`jdeps -jdkinternals <JAR 파일들>`

`jdeps -jdkinternals -cp <클래스패스들>`

### 10.3.3 모듈 행동 오버라이드하기
#### add-reads
모듈 설정을 기준으로 모듈에서 사용할 수 없는 새로운 가독성 관계를 명시
- --add-reads <소스 모듈>=<대상 모류>

#### add-exports
어떤 모듈에 익스포트 패키리르 추가함. 캡슐화를 깨거나 오버라이드할 수 있음
- --add-exports <소스 모듈>/<패키지 이름>=<대상 모듈>

#### add-opens
모듈 간의 opens 관계를 오버라이드하여 리플렉션 접근 허용
- --add opens <소스 모듈>/<패키지 이름>=<대상 모듈>

JAR 파일의 매니페스트 파일에 명시 가능
- Add-Exports -> --add-exports
- Add-Opens -> --add-opens
- --add-reads는 없음

#### --permit-illegal-access
- 오직 클래스패스에 있는 코드에만 동작
- javac 또는 java 명령에 넘기면 모든 가독성과 접근성 제약을 해제
- 즉, 모듈화 기능을 비활성화
- 추천되지 않음
- 마이그레이션을 위한 최후의 보루

#### -add-exports 속성을 추가하여 컴파일
- `javac -d out src/com/packt/app/Application.java --add-exports java.base/sun.util.calendar=ALL-UNNAMED`
- warnings를 나타내고 컴파일 성공
- 캡슐화된 타입 접근 문제만 있으면 위와 같이 해결 가능
- 더는 존재하지 않는 타입을 참조하고 있다면 코드를 수정하고 재컴파일 해야함

## 10.4. 영향력 이해하기
### 하위 호환성
- 대체할 수 있는 API: `jdeps -jdkinternals` 명령으로 확인 가능
- 캡슐화된 API: javac, java 명령을 호출할 때 오버라이드 인자를 추가하는 방법

### jdk.unsupported 모듈
- 캡슐화 하려 하였지만 너무나 널리 사용하고, 대체 API를 제공하지 않으면 마이그레이션에 문제
- 아직 접근할 수 있는 미지원 API
- `java -d jdk.unsupported`
- 나중에 쉽게 퇴화 가능
 
## 10.5. 추천 전략
1. `jdeps -jdkinternals`로 내부 API를 호출하고 있는지 확인
2. 오류가 있고 고칠 수 있으면 jdeps 명령의 권고에 따라 해당 오류 수정
3. 외부 라이브러리에서 오류가 발생했다면 최신 버전으로 업데이트
4. 앞선 단계에 모두 해당 안되면 오버라이드 옵션 고려 (--add-exports, --add-opens 등)
5. `--permit-illegal-access` 킬 스위치로 모든 모듈화 기능 off (최후의 기능)

## 10.6. 요약
- 자바 9 마이그레이션 두 단계 중 첫 단계:  레거시 코들르 자바 9 환경에서 컴파일 & 실행
- 내부 API 접근 없는 자바 8 프로젝트로 자바 9 에서 컴파일 & 실행
- 의도적으로 내부 API에 접근하는 클래스에서 오류 발생 확인
- jdeps 도구 사용하여 검사하고 불법 API 사용 찾음
- 제안 받은 대안 API를 사용하거나 명령줄 옵션 사용사여 자바 9에서 정상적으로 컴파일 & 실행
- 고수준에서 레거시 코드를 자바 9에서 실행하는 전략 알아봄
