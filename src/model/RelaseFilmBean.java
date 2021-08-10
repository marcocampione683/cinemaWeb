package model;

public class RelaseFilmBean extends FilmBean {

	private static final long serialVersionUID = 1L;
	private String relaseDate;

	public RelaseFilmBean() {
		super();
		relaseDate = "";
	}

	public String getRelaseDate() {
		return relaseDate;
	}

	public void setRelaseDate(String relaseDate) {
		this.relaseDate = relaseDate;
	}

}
