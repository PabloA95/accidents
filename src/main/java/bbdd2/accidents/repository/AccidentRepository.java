package bbdd2.accidents.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import bbdd2.accidents.model.Accident;

@Repository
public interface AccidentRepository extends ElasticsearchRepository<Accident,String> {

	public Iterable<Accident> findTop3ByOrderByDistance(); //findTopByOrderByWeightDesc();

	public Optional<Accident> findByNumber(Double number);

	// https://www.baeldung.com/spring-data-elasticsearch-tutorial
	// Armar el JSON de los puntos a mano y pasarlo como string!
	@Query("{\"bool\":{\"must\":{\"match_all\":{}},\"filter\":{\"geo_polygon\":{\"startPos\":{\"points\":[{\"lon\":-80,\"lat\":30},{\"lon\":-80,\"lat\":45},{\"lon\":-90,\"lat\":30},{\"lon\":-90,\"lat\":45}]}}}}}")
	public Iterable<Accident> findInsidePolygon();

}
