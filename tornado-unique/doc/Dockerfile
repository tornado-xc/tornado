FROM openjdk:11
ENV WORKDIR=/app
COPY . ${WORKDIR}
WORKDIR ${WORKDIR}
EXPOSE  55000
CMD ["java", "-jar", "tornado-unique-1.0.0.jar"]
