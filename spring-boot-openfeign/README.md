# OpenFeign

## 테스트

```shell
python3 -m venv venv
source venv/bin/activate
pip install fastapi uvicorn
uvicorn test_server:app --reload --port 38080
```

```shell
./gradlew e2e --rerun
```

## 참조

- [OpenFeign/feign](https://github.com/OpenFeign/feign)
- [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
