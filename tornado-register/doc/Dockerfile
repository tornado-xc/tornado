FROM openjdk:11
ENV WORKDIR=/app
COPY . ${WORKDIR}
WORKDIR ${WORKDIR}
EXPOSE  58761
CMD ["java", "-jar", "tornado-register-1.0.0.jar"]
