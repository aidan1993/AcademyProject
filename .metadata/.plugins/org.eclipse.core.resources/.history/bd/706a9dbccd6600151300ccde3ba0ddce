package project.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Shippers")
public class Shipper {
	
	public Shipper() {
		super();
	}
	
	@Id
	private int shipperid;
	private String companyname;
	private String phone;
	
	public int getShipperid() {
		return shipperid;
	}
	public void setShipperid(int shipperid) {
		this.shipperid = shipperid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "ShipperId: " + shipperid + ", Company: " + companyname + ", Phone: " + phone;
	}
}
