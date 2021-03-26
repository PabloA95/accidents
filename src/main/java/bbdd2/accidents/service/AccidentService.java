package bbdd2.accidents.service;


import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
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

	public Optional<Accident> getById(String id) {
		return accidentRepository.findById(id);
	}

	public Iterable<Accident> getInsideThePolygon(JSONObject jason) {
		String arrayPoints = jason.getJSONArray("points").toString();
		return accidentRepository.findInsidePolygon(arrayPoints);
	}

	public Iterable<Accident> getInsideTheCircle(JSONObject jason) {
		Float distance = Float.parseFloat(jason.get("distance").toString());
		String origin = jason.get("origin").toString();
		
		return accidentRepository.findInsideCircle(distance,origin);
	}

	public JSONObject getMostCommonConditions() {
		// Lista de la seleccio de columnas por las que se desea buscar
		String columnas[] = {"precipitation","distance","temperature","windChill","humidity","pressure","visibility","windSpeed","windDirection","nauticalTwilight","severity","sunriseSunset","civilTwilight","astronomicalTwilight","weatherCondition"}; //"precipitation"
		
		JSONObject jsonResponse = new JSONObject();
		for(String s:columnas) {
			JSONObject aux2= accidentRepository.findMostCommonConditions(s);
			jsonResponse.put(s, aux2);
		}
		return jsonResponse;
	}
}
