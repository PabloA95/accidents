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

	public Optional<Accident> getById() {
		return accidentRepository.findById("6ubPSncBVfl8vnm3LUm_");
	}

	public Iterable<Accident> getInsideThePolygon(JSONObject jason) {
		// Arma el objeto JSON con los puntos del poligono para la consulta, quitando los corchetes del arreglo JSON
		JSONArray jasonArray= jason.getJSONArray("points"); //.toString();
		String aux = jasonArray.toString();
		String polygon=aux.substring(1, aux.length()-1);
		
//		System.out.println(aux);
//		jasonArray.toList().get(0);
//		Iterator<Object> it=jasonArray.iterator();
//		String polygon="";
//		while (it.hasNext()) {
//			 polygon=polygon+it.next();
//			 if(it.hasNext()) polygon=polygon+",";
//		}
		return accidentRepository.findInsidePolygon(polygon);
	}

	public Iterable<Accident> getInsideTheCircle(JSONObject jason) {
		Float distance = Float.parseFloat(jason.get("distance").toString());
		String origin = jason.get("origin").toString();
		
		return accidentRepository.findInsideCircle((int)Math.round(distance),origin);
	}

	public JSONObject getMostCommonConditions() {
		// Lista de la seleccio de columnas por las que se desea buscar
		String columnas[] = {"precipitation","distance","temperature","windChill","humidity","pressure","visibility","windSpeed","windDirection","nauticalTwilight","severity","sunriseSunset","civilTwilight","astronomicalTwilight","weatherCondition"}; //"precipitation"
//		String columnas[] = {"temperature","windChill","humidity","pressure","visibility","windDirection","windSpeed","precipitation","weatherCondition","amenity","crossing","station","trafficCalming","trafficSignal","turningLoop","sunriseSunset","civilTwilight","nauticalTwilight"};
		
		JSONObject jsonResponse = new JSONObject();
		for(String s:columnas) {
			JSONObject aux2= accidentRepository.findMostCommonConditions(s);
			jsonResponse.put(s, aux2);
		}
		return jsonResponse;
	}
}
