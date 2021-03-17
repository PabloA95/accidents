package bbdd2.accidents.service;


import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
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

//	public Iterable<Accident> getTop10() {
//		return accidentRepository.findTop3ByOrderByDistance();
//	}
//
//	public Optional<Accident> getByNumber(){
//		return accidentRepository.findByNumber(2584.0);
//	}

	public Optional<Accident> getById() {
		return accidentRepository.findById("6ubPSncBVfl8vnm3LUm_");
	}

	public Iterable<Accident> getInsidePolygon(String polygon) {
		return accidentRepository.findInsidePolygon(polygon);
	}

	public Iterable<Accident> getDistance(Float distance,String origin) {
		return accidentRepository.findDistance((int)Math.round(distance),origin);
	}

	public String getMostCommonConditions() {
		String columnas[] = {"distance","temperature","windChill","humidity","pressure","visibility","windSpeed","windDirection","nauticalTwilight","severity","sunriseSunset","civilTwilight","astronomicalTwilight","weatherCondition"}; //"precipitation"
//		String columnas[] = {"temperature","windChill","humidity","pressure","visibility","windDirection","windSpeed","precipitation","weatherCondition","amenity","crossing","station","trafficCalming","trafficSignal","turningLoop","sunriseSunset","civilTwilight","nauticalTwilight"};
		
		JSONObject jsonResponse = new JSONObject();
		for(String s:columnas) {
			JSONObject aux2= accidentRepository.findMostCommonConditions(s);
			jsonResponse.put(s, aux2);
		}
		return jsonResponse.toString();
	}
}
