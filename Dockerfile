# Usa una imagen base de OpenJDK 17
FROM openjdk:17-slim

# Crea un volumen temporal
VOLUME /tmp

# Copia tu archivo JAR al contenedor
COPY target/integrador-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Establece variables de entorno para la conexión a la base de datos
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d1asqh15pdvs73d8tlc0-a.oregon-postgres.render.com:5432/db_fap_jkqz
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=wfPEPfW4bbxp7MmfoCJPSehdH2EGrFFa

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
