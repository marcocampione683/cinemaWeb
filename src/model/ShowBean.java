package model;

import java.io.Serializable;

public class ShowBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String showId, titleFilm, date, address, hour;
	private int hall;
	private double price;
	
	public ShowBean() {
		showId = "";
		titleFilm = "";
		date = "";
		address = "";
		hour = "";
		hall = 0;
		price = 0.0;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public String getTitleFilm() {
		return titleFilm;
	}

	public void setTitleFilm(String titleFilm) {
		this.titleFilm = titleFilm;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHall() {
		return hall;
	}

	public void setHall(int hall) {
		this.hall = hall;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
	
	@Override 
	public boolean equals(Object other) {
		return (this.getShowId().equals(((ShowBean)other).getShowId()));
	}

	@Override
	public String toString() {
		return "id spettacolo= "+showId+" titolo= "+titleFilm+" data = "+date+" indirizzo cinema= "+address+
			   " ora spettacolo= "+hour+" sala= "+hall+" prezzo= "+price;
	}

}
