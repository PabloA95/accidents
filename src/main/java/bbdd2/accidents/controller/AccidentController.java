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
import org.json.JSONException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@GetMapping("/api/inside-the-polygon")
	public String getInsidePolygon(@RequestBody(required = false) String points) throws FileNotFoundException, JsonProcessingException{
		if(points==null) return "{\"error\":\"must send parameters, an json array of {lat, lon} pairs of at least 3 elements\"}";
//		return aux("polygonSchema.json", "poligono", points);
//		// Forma de hacerlo sin usar un archivo externo para el esquema del JSON
//		String aux = "{\"definitions\": {},\"$schema\": \"http://json-schema.org/draft-07/schema#\", \"$id\": \"https://example.com/object1616011172.json\", \"title\": \"polygon\", \"type\": \"object\",\"required\": [\"points\"],\"properties\": {\"points\": {\"$id\": \"points\", \"title\": \"Points\", \"type\": \"array\",\"uniqueItems\": true,\"minItems\": 3,\"items\":{\"$id\": \"points/items\", \"title\": \"Items\", \"type\": \"object\",\"required\": [\"lon\",\"lat\"],\"properties\": {\"lon\": {\"$id\": \"points/items/lon\", \"title\": \"Lon\", \"type\": \"number\",\"minimum\": -180,\"maximum\": 180},\"lat\": {\"$id\": \"points/items/lat\", \"title\": \"Lat\", \"type\": \"number\",\"minimum\": -90,\"maximum\": 90}}}}}}";
//		JSONObject jsonSchema = new JSONObject(new JSONTokener(aux));
//		Schema schema = SchemaLoader.load(jsonSchema);
		// Carga el validador de json
		InputStream schemaStream = new FileInputStream("./src/main/resources/schemas/polygonSchema.json");
		JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
	    SchemaLoader loader = SchemaLoader.builder().schemaJson(rawSchema).build();
	    Schema schema =  loader.load().build();
		
	    try {
	    	// Crea el JSON, lo valida y llama al service
			JSONObject jason = new JSONObject(points);
			schema.validate(jason);
			Iterable<Accident> itr = this.accidentService.getInsideThePolygon(jason);
			JSONArray result = new JSONArray(itr);
			return result.toString();
		} catch (ValidationException e) {
			// Convierte la lista de errores en JSON y devuelve el resultado
			List<String> err = e.getAllMessages();
			JSONObject errors= new JSONObject();
			for(String s:err){
				s = s.replace("#/", "");
				String[] parts = s.split(": ");
				String key = parts[0];
				String value = parts[1];
				errors.put(key, value);
			}
			return errors.toString();
		} catch (JSONException e) {
			return (new JSONObject().put("error", e.getMessage())).toString();
		}
	}

	@GetMapping("/api/inside-the-circle")
	public String getInsideCircle(@RequestBody(required = false) String param) throws FileNotFoundException, JsonProcessingException{
		if(param==null) return "{\"error\":\"must send parameters distance, origin.lat and origin.lon\"}";
//		return aux("circleSchema.json", "circulo", param);
		// Carga el validador de json
		InputStream schemaStream = new FileInputStream("./src/main/resources/schemas/circleSchema.json");
		JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
	    SchemaLoader loader = SchemaLoader.builder().schemaJson(rawSchema).build();
	    Schema schema =  loader.load().build();
		
	    try {
	    	// Crea el JSON, lo valida y llama al service
			JSONObject jason = new JSONObject(param);
			schema.validate(jason);
			Iterable<Accident> itr = this.accidentService.getInsideTheCircle(jason);
			JSONArray result = new JSONArray(itr);
			return result.toString();
		} catch (ValidationException e) {
			// Convierte la lista de errores en JSON y devuelve el resultado
			List<String> err = e.getAllMessages();
			JSONObject errors= new JSONObject();
			for(String s:err){
				s = s.replace("#/", "");
				String[] parts = s.split(": ");
				String key = parts[0];
				String value = parts[1];
				errors.put(key, value);
			}
			return errors.toString();
		} catch (JSONException e) {
			return (new JSONObject().put("error", e.getMessage())).toString();
		}
	}
	
	@GetMapping("/api/most-common-conditions")
	public String getMostCommonConditions() {
		return (this.accidentService.getMostCommonConditions()).toString();
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//	---------------------------------
//	public String aux(String schemaFileName, String forma, String json) throws FileNotFoundException, JsonProcessingException{
//		InputStream schemaStream = new FileInputStream("./src/main/resources/schemas/"+schemaFileName);
//		JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
//	    SchemaLoader loader = SchemaLoader.builder().schemaJson(rawSchema).build();
//	    Schema schema =  loader.load().build();
//		
//	    try {
//	    	// Crea el JSON, lo valida y llama al service
//			JSONObject jason = new JSONObject(json);
//			schema.validate(jason);
//			Iterable<Accident> itr = null;
//			if(forma.equals("poligono")) {
//				itr = this.accidentService.getInsideThePolygon(jason);
//			} else if (forma.equals("circulo")){ 
//				itr = this.accidentService.getInsideTheCircle(jason);
//			}
//			JSONArray result = new JSONArray(itr);
//			return result.toString();
//		} catch (ValidationException e) {
//			// Convierte la lista de errores en JSON y devuelve el resultado
//			List<String> err = e.getAllMessages();
//			JSONObject errors= new JSONObject();
//			for(String s:err){
//				s = s.replace("#/", "");
//				String[] parts = s.split(": ");
//				String key = parts[0];
//				String value = parts[1];
//				errors.put(key, value);
//			}
//			return errors.toString();
//		} catch (JSONException e) {
//			return (new JSONObject().put("error", e.getMessage())).toString();
//		}
//	}
}
