## An implementation of thread pool based event consumer
This program uses ThreadPoolExecutor

### Required: 
Java version: 1.17
1. Make sure your JAVA_HOME points to jdk-17 (`echo $JAVA_HOME`)
2. java -version and mvn -version must report `java version 17`

### Configuration
The thread pool executor can be tuned by changing the properties given in
`src/main/resources/application.yml`

### How to run

`mvn spring-boot:run`


