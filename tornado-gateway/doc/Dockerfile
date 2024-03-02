FROM openjdk:11
ENV WORKDIR=/app
COPY . ${WORKDIR}
WORKDIR ${WORKDIR}
EXPOSE  80
CMD ["java", "-jar", "tornado-gateway-1.0.0.jar"]
