# Chapter 2 첫 번째 자바 모듈 만들기

## [Java 9 모듈 프로그래밍, 한빛미디어(코시크 코타갈)](http://www.hanbit.co.kr/store/books/look.php?p_code=B7608640342)
![책이미지](book-image.jpg)

소스: https://github.com/yudong80/Modular-Programming-in-Java-9/tree/master/05-jdk-modules/src/packt.addressbook.ui/packt/addressbook

## 목차
1. JDK 설정하기
2. JDK 버전 전환하기
3. 넷빈즈 IDE 설정하기
4. 자바 9 모듈
    1. 전통적인 자바 코드 구조
    2. 모듈은 무엇인가
    3. 모듈 생성하기
    4. 첫 번째 자바 모듈 만들기
    5. 모듈 컴파일하기
    6. 모듈 실행하기
5. 넷빈즈 활용 모듈 만들기
6. 주소록 관리 프로그램
7. 오류 다루기
8. 요약

## 모듈 정의하기(module-info.java활용)
- 컴파일하고 모듈 실행하기
- 발생할 수 있는 오류 다루기

## 모듈
- 클래스와 패키지처럼 일급 시민이자 새로운 프로그램 컴포넌트
- 생성하고 호출할 수 있는 코드와 데이터의 집합
- 이름이 있고 스스로 의미를 가짐
- 안에 다수의 자바 패키지를 가질 수 있고 각 패키지는 클래스나 인터페이스 같은 자바 코드 요소를 포함
- 응용 프로그램은 모듈들을 응용하여 구성

## 주소록 코드
**Contact.java**
```java
package packt.addressbook.model;

public class Contact implements Comparable {

    private String firstName;
    private String lastName;
    private String phone;

    public Contact(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return this.lastName + " " + this.phone;
    }

    @Override
    public int compareTo(Object other) {
        Contact otherContact = (Contact)other;
        return this.lastName.compareTo(otherContact.lastName);
    }
}
```

**ContactUtil.java**
```java
package packt.addressbook.util;

import packt.addressbook.model.Contact;

import java.util.Arrays;
import java.util.List;

public class ContactUtil {

    public List<Contact> getContacts() {
        return Arrays.asList(
          new Contact("Edsger", "Dijkstra", "345-678-9012"),
          new Contact("Alan", "Turing", "456-789-0123"),
          new Contact("Ada", "Lovelace", "234-567-8901"),
          new Contact("Charles", "Babbage", "123-456-7890"),
          new Contact("Tim", "Berners-Lee", "456-789-0123")
        );
    }
}
```

**SortUtil.java**
```java
package packt.addressbook.util;

import java.util.List;

public class SortUtil {
    public <T extends Comparable> List<T> sortList(List<T> list) {
        for (int outer = 0; outer < list.size()-1; outer++) {
            for (int inner = 0; inner < list.size()-outer-1; inner++) {
                if (list.get(inner).compareTo(list.get(inner+1)) > 0) {
                    swap(list, inner);
                }
            }
        }
        return list;
    }

    private <T> void swap(List<T> list, int inner) {
        T temp = list.get(inner);
        list.set(inner, list.get(inner + 1));
        list.set(inner + 1, temp);
    }
}
```

**Main.java**
```java
package packt.addressbook;

import packt.addressbook.model.Contact;
import packt.addressbook.util.ContactUtil;
import packt.addressbook.util.SortUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ContactUtil contactUtil = new ContactUtil();
        SortUtil sortUtil = new SortUtil();
        List<Contact> contacts = contactUtil.getContacts();
        sortUtil.sortList(contacts);
        System.out.println(contacts);
    }
}
```


## 모듈 컴파일
```
javac -d out --module-source-path src --module packt.addressbook
```
- `-d` out : 컴파일된 출력 폴더(out)
- `--module-source-path` src : 모듈 루트 폴더의 위치(src)
- `--module` packt.addressbook : 컴파일 하고자 하는 모듈 이름(packt.addressbook)

## 모듈 실행하기
```
java --module-path out --module packt.addressbook/packt.addressbook.Main
```
- `--module-path` out : 컴파일 된 클래스의 위치(out). `--module-path`은 `-p`으로 축약 가능
- `--module` packt.addressbook/packt.addressbook.Main : 실행할 모듈이름(packt.addressbook)과 메인 클래스(packt.addressbook.Main). `--module`은 `-m`으로 축약 가능

## 오류 다루기
1. 컴파일 오류

    `javac: invalid flag: --module-source-path`
    
    : JDK 9로 전환하지 않고 자바 8 컴파일러를 그대로 사용할 때 나타남. --module-source-path 옵션은 자바 9의 javac에 새롭게 포함된 옵션임
2. 컴파일 오류

    `error: module not found: packt.addressbook`

    : 자바 컴파일러가 모듈의 module-info.java파일을 찾지 못해서 발생
3. 런타임 오류

    ```
    Error occurred during initialization of VM
    java.lang.module.ResolutionException: Module packt.addressbook not found
    ```

    : 모듈 파일이 모듈 경로에 없어서 발생. packt.addressbook 폴더가 컴파일된 module-info.class 파일을 포함하는지를 확인
