package bbdd2.accidents.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/api/html")
	public String getHtml() {
		return this.accidentService.getHtml();
	}

	@GetMapping("/api/top10")
	public Iterable<Accident> getTop10() {
		return this.accidentService.getTop10();
	}

	@GetMapping("/api/bynumber")
	public Optional<Accident> getByNumber(){
		return this.accidentService.getByNumber();
	}

	@GetMapping("/api/byid")
	public Optional<Accident> getById(){
		return this.accidentService.getById();
	}

	@GetMapping("/api/poligono_hardcodeado")
	public Iterable<Accident> getInPolygon(){
		return this.accidentService.getInsidePolygon();
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
