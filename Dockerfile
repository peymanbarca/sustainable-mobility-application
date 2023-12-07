FROM eclipse-temurin:17-jre


COPY target/*.jar /app.jar

CMD echo "The application is going to start"
CMD exec java ${JAVA_OPTS} -jar app.jar
