package model;

public class AccessBean {
	
	private String id_spettacolo, email, m_accessi;
	
	public AccessBean() {
		id_spettacolo = "";
		email = "";
		m_accessi = "";
	}

	public String getId_spettacolo() {
		return id_spettacolo;
	}

	public void setId_spettacolo(String id_spettacolo) {
		this.id_spettacolo = id_spettacolo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getM_accessi() {
		return m_accessi;
	}

	public void setM_accessi(String m_accessi) {
		this.m_accessi = m_accessi;
	}
	
	
}
