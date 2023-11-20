FROM docker.io/openjdk:17.0.1

ADD ../../mb_project-0.0.1.jar mb_project-0.0.1.jar

RUN  keytool -importcert -file /usr/java/openjdk-17/lib/security/keycloak-self-signed-cert.pem -noprompt -alias keycloakcerts  -storepass changeit -keystore /usr/java/openjdk-17/lib/security/cacerts

ENTRYPOINT ["java", "-jar", "/mb_project-0.0.1.jar"]