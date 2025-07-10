FROM openjdk:17-slim

VOLUME /tmp

RUN apt-get update && apt-get install -y curl && \
    curl -L -o app.jar https://github.com/Wolfred54543/INTEGRADOR_FAP/releases/download/v2.0.0/integrador-0.0.2-SNAPSHOT.jar && \
    apt-get clean

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d1asqh15pdvs73d8tlc0-a.oregon-postgres.render.com:5432/db_fap_jkqz
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=wfPEPfW4bbxp7MmfoCJPSehdH2EGrFFa

ENTRYPOINT ["java", "-jar", "app.jar"]
