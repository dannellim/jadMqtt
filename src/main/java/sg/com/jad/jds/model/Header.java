package sg.com.jad.jds.model;

public class Header {
	
	private Integer ID;
	private Integer SN;
	private Integer mobile_id;
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
	@Override
	public String toString() {
		return "Common [ID=" + ID + ", SN=" + SN + ", mobile_id=" + mobile_id + "]";
	}

}