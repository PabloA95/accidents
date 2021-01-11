package bbdd2.accidents.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import bbdd2.accidents.model.Accident;

@Repository
public interface AccidentRepository extends ElasticsearchRepository<Accident,String> {

	public Iterable<Accident> findTop10ByOrderByDistance(); //findTopByOrderByWeightDesc();

	public Optional<Accident> findByNumber(Double number);
}
