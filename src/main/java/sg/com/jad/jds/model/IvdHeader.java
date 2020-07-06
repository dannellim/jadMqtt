package sg.com.jad.jds.model;

public class IvdHeader {
	
	// variables here
	
	private Integer ID;
	private Integer SN;
	private Integer mobile_id;
	private float latitude;
	private float longtitude;
	private Integer speed;
	private Integer direction;
	private String status;
	private Integer zone;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getSN() {
		return SN;
	}
	public void setSN(Integer sN) {
		SN = sN;
	}
	public Integer getMobile_id() {
		return mobile_id;
	}
	public void setMobile_id(Integer mobile_id) {
		this.mobile_id = mobile_id;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(float longtitude) {
		this.longtitude = longtitude;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getZone() {
		return zone;
	}
	public void setZone(Integer zone) {
		this.zone = zone;
	}
	@Override
	public String toString() {
		return "IVD [ID=" + ID + ", SN=" + SN + ", mobile_id=" + mobile_id + ", latitude=" + latitude + ", longtitude="
				+ longtitude + ", speed=" + speed + ", direction=" + direction + ", status=" + status + ", zone=" + zone
				+ "]";
	}
	
	

}
