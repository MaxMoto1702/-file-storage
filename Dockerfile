FROM openjdk:8-jdk-alpine

# Build-time metadata as defined at http://label-schema.org
ARG BUILD_DATE
#ARG VCS_REF
ARG VERSION
LABEL org.label-schema.build-date=$BUILD_DATE \
      org.label-schema.name="File Storage" \
      org.label-schema.description="File storage for other systems" \
      org.label-schema.url="http://max.serebryansky.com" \
      org.label-schema.vcs-ref=$VCS_REF \
      org.label-schema.vcs-url="https://github.com/maxmoto1702/max-file-storage" \
      org.label-schema.version=$VERSION \
      org.label-schema.schema-version="1.0"

VOLUME /tmp
VOLUME /data
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]