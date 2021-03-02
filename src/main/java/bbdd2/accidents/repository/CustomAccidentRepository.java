package bbdd2.accidents.repository;

import org.json.JSONObject;

public interface CustomAccidentRepository {

	public JSONObject findMostCommonConditions(String s);
}
