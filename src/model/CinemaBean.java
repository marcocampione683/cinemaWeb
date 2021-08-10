package model;

import java.io.Serializable;

public class CinemaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name, city, province, address, country, maps;

	public CinemaBean() {
		name = "";
		city = "";
		province = "";
		address = "";
		country = "";
		maps = "";
	}
	
	public String getMaps() {
		return maps;
	}

	public void setMaps(String maps) {
		this.maps = maps;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override 
	public boolean equals(Object other) {
		return (this.getAddress().equals(((CinemaBean)other).getAddress()));
	}

	@Override
	public String toString() {
		return "nome= "+name+" città= "+city+" provincia= "+province+" indirizzo= "+address+" regione= "+country;
	}

}
