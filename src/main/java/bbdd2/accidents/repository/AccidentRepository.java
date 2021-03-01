package bbdd2.accidents.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import bbdd2.accidents.model.Accident;

@Repository
public interface AccidentRepository extends ElasticsearchRepository<Accident,String>, CustomAccidentRepository {

	public Iterable<Accident> findTop3ByOrderByDistance(); //findTopByOrderByWeightDesc();

	public Optional<Accident> findByNumber(Double number);

	// https://www.baeldung.com/spring-data-elasticsearch-tutorial
	// Armar el JSON de los puntos a mano y pasarlo como string!
	@Query("{\"bool\":{\"must\":{\"match_all\":{}},\"filter\":{\"geo_polygon\":{\"startPos\":{\"points\":[?0]}}}}}")
	public Iterable<Accident> findInsidePolygon(String polygon);


	// Falta pasar lat y lng como parametros
	@Query("{\"bool\":{\"must\":{\"match_all\":{}},\"filter\":{\"geo_distance\":{\"distance\":\"?0km\",\"startPos\":?1}}}}")
	public Iterable<Accident> findDistance(Integer distance,String origin);

//	@Query("{\"query\":\"SELECT pressure FROM prueba_geo_end GROUP BY pressure ORDER BY count(pressure) DESC LIMIT 1\"}")
//	@Query("{\"size\":0,\"aggs\":{\"pressure\":{\"terms\": {\"field\":\"pressure\",\"size\":1,\"order\":{\"_count\":\"desc\"}}}}}")
//	@Query("SELECT pressure FROM prueba_geo_end GROUP BY pressure ORDER BY count(pressure) DESC LIMIT 1")
//	public Object findMostCommonConditions();

}
