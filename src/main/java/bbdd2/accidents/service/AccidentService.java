package bbdd2.accidents.service;


import java.util.Optional;

//import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bbdd2.accidents.model.Accident;
import bbdd2.accidents.repository.AccidentRepository;

@Service
public class AccidentService {

	@Autowired
	private AccidentRepository accidentRepository;


	public Integer getNumber() {
		return 1;
	}

	public String getHtml() {
		return "<h1>Lista de accidentes</h1><h3>Lalala soy un h3</h3>";
	}

	public Iterable<Accident> getTop10() {
		return accidentRepository.findTop3ByOrderByDistance();
	}

	public Optional<Accident> getByNumber(){
		return accidentRepository.findByNumber(2584.0);
	}

	public Optional<Accident> getById() {
		return accidentRepository.findById("6ubPSncBVfl8vnm3LUm_");
	}

	public Iterable<Accident> getInsidePolygon(String polygon) {
		return accidentRepository.findInsidePolygon(polygon);
	}
}
