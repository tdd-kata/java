# 비동기 요청

## 준비

- JDK 11+

## 실행

```shell
./gradlew run
```

## 도구

### Web Client

- WebClient
- RestTemplate
- FeignClient
- Retrofit
- OkHttp
- Unirest
- HttpClient

### Concurrency

- TaskExecutor

## Fake API (free)

- [Reqres](https://reqres.in/)
    - 회원 가입과 로그인 API를 비롯한 다양한 API를 제공합니다.
    - REST API를 지원하며 JSON 포맷으로 응답합니다.
    - example
        - [`/api/users`](https://reqres.in/api/users?page=2)
        - [`/api/users/{id}`](https://reqres.in/api/users/2)
- [JSONPlaceholder](https://jsonplaceholder.typicode.com/)
    - 포스트, 댓글, 할 일 등의 데이터를 생성하는 API를 제공합니다.
    - REST API를 지원하며 JSON 포맷으로 응답합니다.
    - example
        - [`/posts`](https://jsonplaceholder.typicode.com/posts)
        - [`/todos/{id}`](https://jsonplaceholder.typicode.com/todos/1)
- [Faker API](https://fakerapi.it/en)
    - 가짜 데이터를 생성하는 API를 제공합니다.
    - REST API를 지원하며 JSON 포맷으로 응답합니다.
    - example
        - [`/api/v1/companies`](https://fakerapi.it/api/v1/companies?_quantity=10)
        - [`/api/v1/addresses`](https://fakerapi.it/api/v1/addresses?_quantity=10)
        - [`/api/v1/credit_cards`](https://fakerapi.it/api/v1/books?_quantity=10)
        - [`/api/v1/credit_cards`](https://fakerapi.it/api/v1/credit_cards?_quantity=10)
        - [`/api/v1/users`](https://fakerapi.it/api/v1/users?_quantity=10)
        - [`/api/v1/images`](https://fakerapi.it/api/v1/images?_quantity=10)
        - [`/api/v1/custom`](https://fakerapi.it/api/v1/custom?_quantity=1&customfield1=name&customfield2=dateTime&customfield3=phone)
- [Cat Facts API](https://catfact.ninja/)
    - 고양이에 대한 가짜 사실을 생성하는 API를 제공합니다.
    - REST API를 지원하며 JSON 포맷으로 응답합니다.
    - example
        - [`/fact`](https://catfact.ninja/fact)
        - [`/facts`](https://catfact.ninja/facts)
