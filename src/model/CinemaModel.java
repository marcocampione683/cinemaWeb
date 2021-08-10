package model;

import java.sql.SQLException;
import java.util.Collection;

public interface CinemaModel<T> {
	
	public Collection<T> recuperaDatiDaAttributo(String attribute) throws SQLException;
	public T recuperaDatiDaChiave(String key) throws SQLException;
	public Collection<T> recuperaComponenti(String order) throws SQLException;
	public void Salva(T component) throws SQLException;
	public void Aggiorna(T component) throws SQLException;
	public boolean Elimina(String key) throws SQLException; 
	public void AggiornaConChiave(T component, String key) throws SQLException;

}
