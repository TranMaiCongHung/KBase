# --- Stage 1: Build file .jar ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy các file của Maven vào trước để tải thư viện (tối ưu cache)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Cấp quyền thực thi cho mvnw và tải các dependency
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

# Copy toàn bộ code vào và build (bỏ qua chạy test để build nhanh hơn)
COPY src src
RUN ./mvnw package -DskipTests

# --- Stage 2: Chạy ứng dụng ---
# Sử dụng bản JRE nhẹ hơn thay vì JDK để tiết kiệm dung lượng
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy file .jar từ Stage 1 sang Stage 2
COPY --from=build /app/target/*.jar app.jar

# Mở cổng 8080 cho Spring Boot
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
