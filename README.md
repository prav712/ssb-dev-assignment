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

### App URL?
http://localhost:8081/showStations


###Identification in header
All requests to our real time API should send the Client-Identifier header. This should contain a value that describes the application accessing the API. The value should contain your company/organization name, follwed by a dash and the application's name, like mycompany-travelplanner or myname-citymonitor.

### Rest api endpoint

## Get list of bike stations

`GET`
curl -i -H 'Client-Identifier: IDENTIFIER' http://localhost:8081/bikeStationUpdate
 
## Response
"stations": [
      {  
        "station_id":"627",
        "name":"Skøyen Stasjon",
        "address":"Skøyen Stasjon",
        "lat":59.9226729,
        "lon":10.6788129,
        "capacity":20
      },
    ]
 
### Note
Need to provide api key to access google map without warnings
<script src="http://maps.google.com/maps/api/js?key={Your key}"/>


