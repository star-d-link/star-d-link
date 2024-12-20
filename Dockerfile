# Stage 1: Build 단계
FROM gradle:8.10.2-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 캐시 최적화를 위해 필요한 파일 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Gradle 의존성 캐싱
RUN gradle dependencies

# 전체 소스 복사 및 애플리케이션 빌드
COPY . .
RUN gradle bootJar

# Stage 2: Run 단계
FROM openjdk:17.0-jdk-slim

COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
LABEL authors="sangwopark"
ENTRYPOINT ["java", "-jar", "/app.jar"]