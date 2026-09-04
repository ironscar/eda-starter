# build stage
FROM <MAVEN3> AS build

# create app directory and go into it
WORKDIR /app

# copy settings.xml, pom and cache dependencies until pom changes
COPY pom.xml ./

# copy source code
COPY src ./src
RUN mvn clean package -DskipTests

# ------------------------------------------------------------------------------------- #

# run stage
FROM <JRE25>

# create app directory and go into it
WORKDIR /app

# copy built WAR
COPY --from=build /app/target/*.jar spring-eda-tgt.jar

# export port and run app
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "spring-eda-tgt.jar"]
