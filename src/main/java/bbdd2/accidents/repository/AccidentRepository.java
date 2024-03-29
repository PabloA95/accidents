package bbdd2.accidents.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import bbdd2.accidents.model.Accident;

@Repository
public interface AccidentRepository extends ElasticsearchRepository<Accident,String>, CustomAccidentRepository {

	@Query("{\"bool\":{\"must\":{\"match_all\":{}},\"filter\":{\"geo_polygon\":{\"startPos\":{\"points\":?0}}}}}")
	public Iterable<Accident> findInsidePolygon(String polygon);

	@Query("{\"bool\":{\"must\":{\"match_all\":{}},\"filter\":{\"geo_distance\":{\"distance\":\"?0km\",\"startPos\":?1}}}}")
	public Iterable<Accident> findInsideCircle(Float distance,String origin);

}
