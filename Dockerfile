FROM adoptopenjdk/openjdk11
VOLUME /tmp
ARG JAR_FILE=build/libs/hc-order-service.jar
ADD ${JAR_FILE} hc-order-service.jar
EXPOSE 5000
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","hc-order-service.jar"]
