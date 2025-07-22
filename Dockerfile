FROM openjdk:17-slim

VOLUME /tmp

RUN apt-get update && apt-get install -y curl && \
    curl -L -o app.jar https://github.com/Wolfred54543/INTEGRADOR_FAP/releases/download/v1.0.0/integrador-0.0.1-SNAPSHOT.jar && \
    apt-get clean

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d1vljevdiees73bplei0-a.oregon-postgres.render.com/db_fap_sueg   
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=iHJz3BAHevoAsWn7jTEE2ZSnY8zak0oh

ENTRYPOINT ["java", "-jar", "app.jar"]
