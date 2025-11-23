FROM azul/zulu-openjdk:21-latest AS builder

WORKDIR /sep

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM azul/zulu-openjdk:21-jre

WORKDIR /sep

COPY --from=builder /sep/target/Student-Events-Platform-1.0.0.jar /sep/sep.jar

ENTRYPOINT ["java", "-jar", "sep.jar"]
EXPOSE 8080