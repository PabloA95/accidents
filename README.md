Antes de ejecutar:
./bin/logstash -f ./logstash-doc.conf

se debe configurar el template para el indice a crear, ejecutando:
$ curl -X PUT "localhost:9200/_template/nombre-del-indice" -H 'Content-Type: application/json' -d '{"order":0,"index_patterns":"nombre-del-indice","mappings":{"properties":{"startPos":{"type":"geo_point"},"endPos":{"type":"geo_point"}}}}'

Para revisar que se haya creado con exito ingresar a:

http://localhost:9200/_template/nombre-del-indice?pretty

Luego de cargar el indice, probar que se haya cargado correctamente en:

http://localhost:9200/nombre-del-indice/_search?pretty



Ejecutar curl -X PUT http://localhost:9200/nombre-del-indice/_mapping -H 'Content-Type: application/json' -d '{"properties":{"NOMBRE_DE_LA_COLUMNA":{"type":"text","fielddata":true}}}' para cada attributo de tipo text que quiera ser consultado en las condiciones comunes. 


---

Para realizar consulta geoespacial de prueba para la ubicacion dentro de un poligono:
$ curl -X GET http://localhost:9200/nombre-del-indice/_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match_all":{}},"filter":{"geo_polygon":{"startPos":{"points":[{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}}}}}}'

Para realizar consulta geoespacial de prueba para los puntos ubicados dentro de cierta distancia
curl -X GET http://localhost:9200/nombre-del-indice/_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match_all":{}},"filter":{"geo_distance":{"distance":"25km","startPos":{"lat":40,"lon":-84}}}}}}'


---

Consultar la API por los puntos a cierta distancia:

$ curl http://localhost:8080/api/distance -d '{"distance":25,"origin":{"lat":40,"lon":-84}}' -X GET

Por los puntos dentro del poligono:

$ curl http://localhost:8080/api/prueba_parametros -d '{"points":[{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}' -X GET
