FROM openjdk:latest
WORKDIR /app
COPY dependencies/ ./
RUN true
COPY spring-boot-loader/ ./
RUN true
COPY application/ ./
RUN true
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS='-Xms128M -Xmx4G -Dfile.encoding=utf-8 -Dspring.profile.active=shoppy'
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
