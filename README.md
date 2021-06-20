# Springboot app showing oslo city bike stations with bikes and docks availabilities.
# An api to find available oslo city bike stations containing bikes and docks availabilities.
### How to build?
```
mvn install
```

### How to run?
##### Without docker
```
mvn spring-boot:run
```
##### With docker
```
(not implemented yet) docker-compose up
```

### How to debug?
Use the following command to start and execute a remote debugger through IDEA (Execution will stop until debugger is 
started)
```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
### How to deploy?
(not implemented yet) 

### Rest api endpoint

http://localhost:8081/bikeStationUpdate

Header: 



### Note
Need to provide api key to access google map without warnings
<script src="http://maps.google.com/maps/api/js?key={Your key}"/>


