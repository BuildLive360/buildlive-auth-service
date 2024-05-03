FROM openjdk:17
EXPOSE 8070
ADD target/buildlive-authservice.jar buildlive-authservice.jar
ENTRYPOINT ["java","-jar","/buildlive-authservice.jar"]