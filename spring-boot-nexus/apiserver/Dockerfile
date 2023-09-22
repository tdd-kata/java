# skopeo --override-os linux inspect docker://gradle | jq '.RepoTags'
FROM gradle:8.1.1-jdk17-jammy AS builder
WORKDIR /usr/src/app
COPY . .
RUN ls -al /usr/src/app
RUN ["gradle", "build", "-i", "--stacktrace"]

# apt install skopeo jq
# skopeo --override-os linux inspect docker://openjdk | jq '.RepoTags'
# ea: early access
# rc: release candidate
FROM openjdk:17-jdk-slim-bullseye
RUN ls -al /usr
COPY --from=builder /usr/src/app/build/libs/apiserver.jar .
CMD ["java", "-jar", "-Dspring.profiles.active=local", "apiserver.jar"]
