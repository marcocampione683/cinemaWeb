package model;

public class ProjectionFilmBean extends FilmBean {

	private static final long serialVersionUID = 1L;
	private String startDate, endDate;

	public ProjectionFilmBean() {
		super();
		startDate = "";
		endDate = "";
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
