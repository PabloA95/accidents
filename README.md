Antes de ejecutar:
./bin/logstash -f ./logstash-doc.conf

se debe configurar el template para el indice a crear, ejecutando:
$ curl -X PUT "localhost:9200/_template/prueba_geo_end" -H 'Content-Type: application/json' -d '{"order":0,"index_patterns":"prueba_geo_end","mappings":{"properties":{"startPos":{"type":"geo_point"},"endPos":{"type":"geo_point"}}}}'

Para revisar que se haya creado con exito ingresar a:

http://localhost:9200/_template/prueba_geo_end?pretty



---

Para realizar consulta geoespacial de prueba:
$ curl -X GET http://localhost:9200/prueba_geo_end/_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match_all":{}},"filter":{"geo_polygon":{"startPos":{"points":[{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}}}}}}'
