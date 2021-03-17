package bbdd2.accidents.model;

//import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(
	    indexName = "testing",
	    shards = 1, 
	    replicas = 0, 
	    refreshInterval = "-1"
	)
public class Accident {

	@Id
	private String id;
	private String source;
	private String TMC;
	private String severity;
	private String startTime;
	private String endTime;
	private String startLat;
	private String startLng;
	private String endLat;
	private String endLng;
	private Float distance;
	private String description;
	private String number;
	private String street;
	private String side;
	private String city;
	private String county;
	private String state;
	private String zipcode;
	private String country;
	private String timezone;
	private String airportCode;
	private String weatherTimestamp;
	private Float temperature;
	private Float windChill;
	private Float humidity;
	private Float pressure;
	private Float visibility;
	private String windDirection;
	private Float windSpeed;
	private Float precipitation;
	private String weatherCondition;
	private Boolean amenity;
	private Boolean bump;
	private Boolean crossing;
	private Boolean giveWay;
	private Boolean junction;
	private Boolean noExit;
	private Boolean railway;
	private Boolean roundabout;
	private Boolean station;
	private Boolean stop;
	private Boolean trafficCalming;
	private Boolean trafficSignal;
	private Boolean turningLoop;
	private String sunriseSunset;
	private String civilTwilight;
	private String nauticalTwilight;
	private String astronomicalTwilight;

	public String getID() {
		return id;
	}
	public void setID(String iD) {
		id = iD;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTMC() {
		return TMC;
	}
	public void setTMC(String tMC) {
		TMC = tMC;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartLat() {
		return startLat;
	}
	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}
	public String getStartLng() {
		return startLng;
	}
	public void setStartLng(String startLng) {
		this.startLng = startLng;
	}
	public String getEndLat() {
		return endLat;
	}
	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}
	public String getEndLng() {
		return endLng;
	}
	public void setEndLng(String endLng) {
		this.endLng = endLng;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getWeatherTimestamp() {
		return weatherTimestamp;
	}
	public void setWeatherTimestamp(String weatherTimestamp) {
		this.weatherTimestamp = weatherTimestamp;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public Float getWindChill() {
		return windChill;
	}
	public void setWindChill(Float windChill) {
		this.windChill = windChill;
	}
	public Float getHumidity() {
		return humidity;
	}
	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}
	public Float getPressure() {
		return pressure;
	}
	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}
	public Float getVisibility() {
		return visibility;
	}
	public void setVisibility(Float visibility) {
		this.visibility = visibility;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public Float getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(Float windSpeed) {
		this.windSpeed = windSpeed;
	}
	public Float getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(Float precipitation) {
		this.precipitation = precipitation;
	}
	public String getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	public Boolean getAmenity() {
		return amenity;
	}
	public void setAmenity(Boolean amenity) {
		this.amenity = amenity;
	}
	public Boolean getBump() {
		return bump;
	}
	public void setBump(Boolean bump) {
		this.bump = bump;
	}
	public Boolean getCrossing() {
		return crossing;
	}
	public void setCrossing(Boolean crossing) {
		this.crossing = crossing;
	}
	public Boolean getGiveWay() {
		return giveWay;
	}
	public void setGiveWay(Boolean giveWay) {
		this.giveWay = giveWay;
	}
	public Boolean getJunction() {
		return junction;
	}
	public void setJunction(Boolean junction) {
		this.junction = junction;
	}
	public Boolean getNoExit() {
		return noExit;
	}
	public void setNoExit(Boolean noExit) {
		this.noExit = noExit;
	}
	public Boolean getRailway() {
		return railway;
	}
	public void setRailway(Boolean railway) {
		this.railway = railway;
	}
	public Boolean getRoundabout() {
		return roundabout;
	}
	public void setRoundabout(Boolean roundabout) {
		this.roundabout = roundabout;
	}
	public Boolean getStation() {
		return station;
	}
	public void setStation(Boolean station) {
		this.station = station;
	}
	public Boolean getStop() {
		return stop;
	}
	public void setStop(Boolean stop) {
		this.stop = stop;
	}
	public Boolean getTrafficCalming() {
		return trafficCalming;
	}
	public void setTrafficCalming(Boolean trafficCalming) {
		this.trafficCalming = trafficCalming;
	}
	public Boolean getTrafficSignal() {
		return trafficSignal;
	}
	public void setTrafficSignal(Boolean trafficSignal) {
		this.trafficSignal = trafficSignal;
	}
	public Boolean getTurningLoop() {
		return turningLoop;
	}
	public void setTurningLoop(Boolean turningLoop) {
		this.turningLoop = turningLoop;
	}
	public String getSunriseSunset() {
		return sunriseSunset;
	}
	public void setSunriseSunset(String sunriseSunset) {
		this.sunriseSunset = sunriseSunset;
	}
	public String getCivilTwilight() {
		return civilTwilight;
	}
	public void setCivilTwilight(String civilTwilight) {
		this.civilTwilight = civilTwilight;
	}
	public String getNauticalTwilight() {
		return nauticalTwilight;
	}
	public void setNauticalTwilight(String nauticalTwilight) {
		this.nauticalTwilight = nauticalTwilight;
	}
	public String getAstronomicalTwilight() {
		return astronomicalTwilight;
	}
	public void setAstronomicalTwilight(String astronomicalTwilight) {
		this.astronomicalTwilight = astronomicalTwilight;
	}

	
}