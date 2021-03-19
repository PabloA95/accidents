# Carga de la base de datos

Primero se debe configurar el template para el indice a crear estableciendo los atributos que seran de tipo geo_point, ejecutando:
_$ curl -X PUT "localhost:9200/\_template/**nombre-del-indice**" -H 'Content-Type: application/json' -d '{"order":0,"index\_patterns":"**nombre-del-indice**","mappings":{"properties":{"startPos":{"type":"geo\_point"},"endPos":{"type":"geo\_point"}}}}'_

Para revisar que se haya creado con exito ingresar a (analizar que startPos y endPos sean del tipo correspondiente):
_http://localhost:9200/\_template/**nombre-del-indice**?pretty_

Luego para cargar los datos en la base de datos mediante logstash, configurar correctamente logstash-doc.conf con los path correspondientes y el nombre del indice, y ejecutar:
_$ ./bin/logstash -f ./logstash-doc.conf_

Luego de cargar el indice, probar que se haya cargado correctamente en:
_http://localhost:9200/**nombre-del-indice**/\_search?pretty_


Para cada attributo de tipo texto que quiera ser consultado en las condiciones comunes ejecutar (necesario para poder utilizar agregacion en las columnas de texto):
_$ curl -X PUT http://localhost:9200/**nombre-del-indice**/\_mapping -H 'Content-Type: application/json' -d '{"properties":{"**NOMBRE\_DE\_LA\_COLUMNA**":{"type":"text","fielddata":true}}}'_


Para la API desarrollada se debe ejecutar:
_$ url -X PUT http://localhost:9200/**nombre-del-indice**/\_mapping -H 'Content-Type: application/json' -d '{"properties":{"nauticalTwilight":{"type":"text","fielddata":true}, "civilTwilight":{"type":"text","fielddata":true}, "weatherCondition":{"type":"text","fielddata":true},"severity":{"type":"text","fielddata":true}, "sunriseSunset":{"type":"text","fielddata":true}, "astronomicalTwilight":{"type":"text","fielddata":true}, "windDirection":{"type":"text","fielddata":true}}}'_



# Pruebas de indices geo_point

Para realizar consulta geoespacial de prueba para la ubicacion dentro de un poligono:
_$ curl -X GET http://localhost:9200/**nombre-del-indice**/\_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match\_all":{}},"filter":{"geo\_polygon":{"startPos":{"points":[{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}}}}}}'_

Para realizar consulta geoespacial de prueba para los puntos ubicados dentro de cierto radio:
_$ curl -X GET http://localhost:9200/**nombre-del-indice**/\_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match\_all":{}},"filter":{"geo\_distance":{"distance":"25km","startPos":{"lat":40,"lon":-84}}}}}}'_


# Consultas para realizar a la API

### Consultar la API por los accidentes dentro de un radio determinado:

_$ curl http://localhost:8080/api/inside-the-circle -d '{"distance": **distance**,"origin":{"lat":**latitud**,"lon":**longitud**}}' -X GET_

e.g.
`$ curl http://localhost:8080/api/inside-the-circle -d '{"distance":25,"origin":{"lat":40,"lon":-84}}' -X GET`


### Por los accidentes dentro de un poligono:

_$ curl http://localhost:8080/api/inside-the-polygon -d '{"points": [{"lon":**longitud1**,"lat":**latitud1**},{"lon":**longitud2**,"lat":**latitud2**},...,{"lon":**longitudN**,"lat":**latitudN**}]}' -X GET_

e.g.
`$ curl http://localhost:8080/api/inside-the-polygon -d '{"points": [{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}' -X GET`


### Por las condiciones mas comunes:

_$ curl http://localhost:8080/api/most-common-conditions -X GET_