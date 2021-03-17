package bbdd2.accidents.controller;

import java.util.Iterator;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
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

//	@GetMapping("/api/poligono_hardcodeado")
//	public Iterable<Accident> getInPolygon(){
//		return this.accidentService.getInsidePolygon();
//	}

	@GetMapping("/api/prueba_parametros")
//	public String pruebaParametros(@RequestParam (value = "points", required = true) String points){
	public Iterable<Accident> pruebaParametros(@RequestBody String points){
		JSONObject jason = new JSONObject(points);
//		jason.get("points");
		JSONArray jasonArray= jason.getJSONArray("points"); //.toString();
		jasonArray.toList().get(0);
		Iterator<Object> it=jasonArray.iterator();
		String polygon="";
		while (it.hasNext()) {
//			Object act= it.next();
//			System.out.println(act);
			 polygon=polygon+it.next();
			 if(it.hasNext()) polygon=polygon+",";
		}
//		System.out.println(polygon);
		return this.accidentService.getInsidePolygon(polygon);
//		return polygon;
	}

	@GetMapping("/api/distance")
//	public String pruebaParametros(@RequestParam (value = "points", required = true) String points){
	public Object getDistance(@RequestBody String param){
		JSONObject jason = new JSONObject(param);
		Float distance = Float.parseFloat(jason.get("distance").toString());
		String origin = jason.get("origin").toString();
//		System.out.println(origin);
		return this.accidentService.getDistance(distance,origin);
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
