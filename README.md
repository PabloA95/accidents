Antes de ejecutar:
./bin/logstash -f ./logstash-doc.conf

se debe configurar el template para el indice a crear, ejecutando:
$ curl -X PUT "localhost:9200/_template/nombre-del-indice" -H 'Content-Type: application/json' -d '{"order":0,"index_patterns":"pruba_mutate5","mappings":{"properties":{"startPos":{"type":"geo_point"}}}}'


*Falta modificar la posicion final como geo_point

---

Para realizar consulta geoespacial de prueba:
$ curl -X GET http://localhost:9200/pruba_mutate5/_search?pretty -H 'Content-Type: application/json' -d '{"query":{"bool":{"must":{"match_all":{}},"filter":{"geo_polygon":{"startPos":{"points":[{"lon":-80,"lat":30},{"lon":-80,"lat":45},{"lon":-90,"lat":30},{"lon":-90,"lat":45}]}}}}}}'
