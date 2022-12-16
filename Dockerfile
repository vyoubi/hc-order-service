FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/hc-order-service.jar
ADD ${JAR_FILE} hc-order-service.jar
EXPOSE 5004
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","hc-order-service.jar"]
