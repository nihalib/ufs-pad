# Base image as java 11
FROM mcr.microsoft.com/java/jre:11-zulu-alpine

# Copy JAVA_OPTS runtime
COPY docker-entrypoint.sh /

# Providing executable permission
RUN chmod +x /docker-entrypoint.sh

# Copy application to base directory
COPY ufs-api/target/*.jar /app/bin/app.jar

EXPOSE 8800

ENTRYPOINT ["/docker-entrypoint.sh"]

