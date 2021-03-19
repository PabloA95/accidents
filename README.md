# Carga de la base de datos

Primero se debe configurar el template para el indice a crear estableciendo los atributos que seran de tipo geo_point, ejecutando:
```
$ curl -X PUT "localhost:9200/_template/nombre-del-indice" -H 'Content-Type: application/json' -d '{"order":0,"index_patterns":"nombre-del-indice","mappings":{"properties":{"startPos":{"type":"geo_point"},"endPos":{"type":"geo_point"}}}}'
```

Para revisar que se haya creado con exito (analizar que startPos y endPos sean del tipo correspondiente), ingresar a:  
`http://localhost:9200/_template/nombre-del-indice?pretty`

Luego para cargar los datos en la base de datos mediante logstash, configurar correctamente logstash-doc.conf con los path correspondientes y el nombre del indice, y ejecutar:
```
$ ./bin/logstash -f ./logstash-doc.conf
```

Luego de cargar el indice, probar que se haya cargado correctamente en:  
`http://localhost:9200/nombre-del-indice/_search?pretty`


Para cada attributo de tipo texto que quiera ser consultado en las condiciones comunes (necesario para poder utilizar agregacion en las columnas de texto), ejecutar:  
`$ curl -X PUT http://localhost:9200/nombre-del-indice/_mapping -H 'Content-Type: application/json' -d '{"properties":{"NOMBRE-DEL-ATRIBUTO":{"type":"text","fielddata":true}}}'`


Para la api desarrollada se debe ejecutar:
```
$ url -X PUT http://localhost:9200/nombre-del-indice/_mapping -H 'Content-Type: application/json' -d '{"properties":{"nauticalTwilight":{"type":"text","fielddata":true}, "civilTwilight":{"type":"text","fielddata":true}, "weatherCondition":{"type":"text","fielddata":true},"severity":{"type":"text","fielddata":true}, "sunriseSunset":{"type":"text","fielddata":true}, "astronomicalTwilight":{"type":"text","fielddata":true}, "windDirection":{"type":"text","fielddata":true}}}'
```


# Pruebas de indices geo_point

Para realizar consulta geoespacial de prueba para la ubicacion dentro de un poligono:  
`$ curl -X GET http://localhost:9200/nombre-del-indice/_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match_all":{}},"filter":{"geo_polygon":{"startPos":{"points":[{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}}}}}}'`

Para realizar consulta geoespacial de prueba para los puntos ubicados dentro de cierto radio:  
`$ curl -X GET http://localhost:9200/nombre-del-indice/_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match_all":{}},"filter":{"geo_distance":{"distance":"25km","startPos":{"lat":40,"lon":-84}}}}}}'`

# Configuracion de la base de datos en la API

Se debera configurar el nombre del indice creado en _src/main/resources/application.properties_


# Consultas para realizar a la API

### Consultar la API por los accidentes dentro de un radio determinado:

```
$ curl http://localhost:8080/api/inside-the-circle -d '{"distance": **distance**,"origin":{"lat":**latitud**,"lon":**longitud**}}' -X GET
```

e.g.  
`$ curl http://localhost:8080/api/inside-the-circle -d '{"distance":25,"origin":{"lat":40,"lon":-84}}' -X GET`


### Por los accidentes dentro de un poligono:

```
$ curl http://localhost:8080/api/inside-the-polygon -d '{"points": [{"lon":**longitud1**,"lat":**latitud1**},{"lon":**longitud2**,"lat":**latitud2**},...,{"lon":**longitudN**,"lat":**latitudN**}]}' -X GET
```

e.g.  
`$ curl http://localhost:8080/api/inside-the-polygon -d '{"points": [{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}' -X GET`


### Por las condiciones mas comunes:

```
$ curl http://localhost:8080/api/most-common-conditions -X GET
```
