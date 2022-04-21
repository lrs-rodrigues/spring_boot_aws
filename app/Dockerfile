FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/spring_boot_aws_ecs-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT [ "java", "-jar", "spring_boot_aws_ecs-0.0.1-SNAPSHOT.jar" ]

EXPOSE 80