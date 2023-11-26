# 파일 분할 업로드

- MOVED TO [markruler/spring-boot-media](https://github.com/markruler/spring-boot-media)

```shell
spring init \
  --boot-version=2.7.17 \
  --java-version=17 \
  --dependencies=web \
  --type=gradle-project \
  --group-id=com.example \
  --version=0.1.0 \
  spring-boot-chunk-file-upload
```

```shell
# fallocate -l 1G test.dat
fallocate -l 500M test.dat
```

# 참조

- [Will Spring hold contents in memory or stores in the disk?](https://stackoverflow.com/questions/1952633) - Stack Overflow
