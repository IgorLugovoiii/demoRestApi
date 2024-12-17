FROM openjdk:21-jdk-slim

# Встановлюємо робочу директорію всередині контейнера
WORKDIR /app

# Копіюємо JAR-файл у контейнер
COPY target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Вказуємо команду для запуску застосунку
ENTRYPOINT ["java", "-jar", "demo.jar"]

# Відкриваємо порт, на якому працює Spring Boot (наприклад, 8080)
EXPOSE 8080
