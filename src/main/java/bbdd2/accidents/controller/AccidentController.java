package bbdd2.accidents.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import bbdd2.accidents.model.Accident;
import bbdd2.accidents.service.AccidentService;

@RestController
public class AccidentController extends AbstractController {

	@Autowired
	private AccidentService accidentService;

	@GetMapping("/api/number")
	public Integer getNumber() {
		return this.accidentService.getNumber();
	}

	@GetMapping("/api/byid")
	public Optional<Accident> getById(){
		return this.accidentService.getById();
	}

	@GetMapping("/api/prueba_parametros")
	public Object pruebaParametros(@RequestBody String points) throws FileNotFoundException{
		InputStream schemaStream = new FileInputStream("./src/main/java/bbdd2/accidents/controller/polygonSchema.json");
		JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
	    SchemaLoader loader = SchemaLoader.builder().schemaJson(rawSchema).build();
	    Schema schema =  loader.load().build();
	    
//		String aux = "{\"definitions\": {},\"$schema\": \"http://json-schema.org/draft-07/schema#\", \"$id\": \"https://example.com/object1616011172.json\", \"title\": \"polygon\", \"type\": \"object\",\"required\": [\"points\"],\"properties\": {\"points\": {\"$id\": \"points\", \"title\": \"Points\", \"type\": \"array\",\"uniqueItems\": true,\"minItems\": 3,\"items\":{\"$id\": \"points/items\", \"title\": \"Items\", \"type\": \"object\",\"required\": [\"lon\",\"lat\"],\"properties\": {\"lon\": {\"$id\": \"points/items/lon\", \"title\": \"Lon\", \"type\": \"number\",\"minimum\": -180,\"maximum\": 180},\"lat\": {\"$id\": \"points/items/lat\", \"title\": \"Lat\", \"type\": \"number\",\"minimum\": -90,\"maximum\": 90}}}}}}";
//		JSONObject jsonSchema = new JSONObject(new JSONTokener(aux));
//		Schema schema = SchemaLoader.load(jsonSchema);
		JSONObject jason = new JSONObject(points);
		try {
			schema.validate(jason);
			JSONArray jasonArray= jason.getJSONArray("points"); //.toString();
//			jasonArray.toList().get(0);
			String aux = jasonArray.toString();
			String polygon=aux.substring(1, aux.length()-1);
			
			System.out.println(aux);
//			Iterator<Object> it=jasonArray.iterator();
//			String polygon="";
//			while (it.hasNext()) {
//				 polygon=polygon+it.next();
//				 if(it.hasNext()) polygon=polygon+",";
//			}
			return this.accidentService.getInsidePolygon(polygon);
		} catch (ValidationException e) {
			List<String> err = e.getAllMessages();
			JSONObject errors= new JSONObject();
			for(String s:err){
				String[] parts = s.split(": ");
				String key = parts[0].substring(2, parts[0].length());
				String value = parts[1];
				errors.put(key, value);
			}
			return errors.toString();
		}
	}

	@GetMapping("/api/distance")
	public Object getDistance(@RequestBody String param) throws FileNotFoundException{
		InputStream schemaStream = new FileInputStream("./src/main/java/bbdd2/accidents/controller/circleSchema.json");
		JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
	    SchemaLoader loader = SchemaLoader.builder().schemaJson(rawSchema).build();
	    Schema schema =  loader.load().build();
	    
//		String aux = "{\"$schema\": \"http://json-schema.org/draft-07/schema#\", \"$id\": \"https://example.com/object1616001697.json\", \"title\": \"Root\", \"type\": \"object\",\"required\": [\"distance\",\"origin\"],\"properties\": {\"distance\": {\"$id\": \"distance\", \"title\": \"Distance\", \"type\": \"number\",\"exclusiveMinimum\":0},\"origin\": {\"$id\": \"origin\", \"title\": \"Origin\", \"type\": \"object\",\"required\": [\"lat\",\"lon\"],\"properties\": {\"lat\": {\"title\": \"Lat\", \"type\": \"number\",\"minimum\": -90,\"maximum\": 90},\"lon\": {\"title\": \"Lon\", \"type\": \"number\",\"minimum\": -180,\"maximum\": 180}}}}}";
//		JSONObject jsonSchema = new JSONObject(new JSONTokener(aux));
//		Schema schema = SchemaLoader.load(jsonSchema);
		JSONObject jason = new JSONObject(param);
		try {
			schema.validate(jason);
			Float distance = Float.parseFloat(jason.get("distance").toString());
			String origin = jason.get("origin").toString();
			return this.accidentService.getDistance(distance,origin);
		} catch (ValidationException e) {
			List<String> err = e.getAllMessages();
			JSONObject errors= new JSONObject();
			for(String s:err){
				String[] parts = s.split(": ");
				String key = parts[0].substring(2, parts[0].length());
				String value = parts[1];
				errors.put(key, value);
			}
			return errors.toString();
		}
	}
	
	@GetMapping("/api/condiciones")
	public String getMostCommonConditions() {
		return this.accidentService.getMostCommonConditions();
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
