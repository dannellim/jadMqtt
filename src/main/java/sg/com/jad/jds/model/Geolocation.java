package sg.com.jad.jds.model;

public class Geolocation {
	
	private double lat;
	private double lon;
	private int speed;
	private int direction;
	
	public Geolocation(double lat, double lon, int speed, int direction) {
		this.lat = lat;
		this.lon = lon;
		this.speed = speed;
		this.direction = direction;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "Geolocation [lat=" + lat + ", lon=" + lon + ", speed=" + speed + ", direction=" + direction + "]";
	}

}
