package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ShowModel implements CinemaModel<ShowBean> {

	private static final String TABLE_NAME = "spettacolo";

	@Override
	public void AggiornaConChiave(ShowBean updateSpettacolo, String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		
		String updateSQL2 = "UPDATE ora SET orario = ? WHERE id_spettacolo = ?";
		
		String updateSQL = "UPDATE " + ShowModel.TABLE_NAME + " SET" +
				" id_spettacolo = ?,titolo_film = ?,data_s = ?,sala = ?,prezzo = ?,indirizzo = ? WHERE id_spettacolo = ?";
		
		
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			
			preparedStatement = connection.prepareStatement(updateSQL);	
			preparedStatement2 = connection.prepareStatement(updateSQL2);
			
			preparedStatement2.setString(1, updateSpettacolo.getHour());
			preparedStatement2.setString(2, key);
			
			System.out.println("doUpdate: "+ preparedStatement2.toString());
			preparedStatement2.executeUpdate();
			
			preparedStatement.setString(1, updateSpettacolo.getShowId());
			preparedStatement.setString(2, updateSpettacolo.getTitleFilm());
			preparedStatement.setString(3, updateSpettacolo.getDate());
			preparedStatement.setInt(4, updateSpettacolo.getHall());
			preparedStatement.setDouble(5, updateSpettacolo.getPrice());
			preparedStatement.setString(6, updateSpettacolo.getAddress());
			preparedStatement.setString(7, key);	
			
			System.out.println("doUpdate: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
						
			connection.commit();
			
		} finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(preparedStatement2 != null) 
					preparedStatement2.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	@Override
	public ShowBean recuperaDatiDaChiave(String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;

		ShowBean bean = new ShowBean();
		
		String selectSQL = "SELECT * FROM spettacolo WHERE id_spettacolo = ?";
		String selectSQLora = "SELECT orario FROM ora where id_spettacolo=?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, key);
			
			System.out.println("recuperaDatiDaChiave: "+ preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				bean.setShowId(rs.getString("id_spettacolo"));
				bean.setTitleFilm(rs.getString("titolo_film"));
				bean.setDate(rs.getString("data_s"));
				bean.setHall(rs.getInt("sala"));
				bean.setPrice(rs.getDouble("prezzo"));
				bean.setAddress(rs.getString("indirizzo"));
			}
			
			preparedStatement2 = connection.prepareStatement(selectSQLora);
			preparedStatement2.setString(1, key);
			ResultSet rs2 = preparedStatement2.executeQuery();
			
			while(rs2.next()) {
				bean.setHour(rs2.getString("orario"));
			}
			
			System.out.println(bean);
		} finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return bean;
	
	}

	@Override
	public Collection<ShowBean> recuperaComponenti(String order) throws SQLException {
		return null;
	}

	@Override
	public void Salva(ShowBean spettacolo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		
		String insertSQL = "INSERT INTO " + ShowModel.TABLE_NAME
							+ " (id_spettacolo,titolo_film,data_s,sala,prezzo,indirizzo)"
							+ " values(?,?,?,?,?,?)";
		
		String insertSQL2 = "INSERT INTO ora"  
				+ "(id_spettacolo,orario)"
				+ "values(?,?)";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, spettacolo.getShowId());
			preparedStatement.setString(2, spettacolo.getTitleFilm());
			preparedStatement.setString(3, spettacolo.getDate());
			preparedStatement.setInt(4, spettacolo.getHall());
			preparedStatement.setDouble(5, spettacolo.getPrice());
			preparedStatement.setString(6, spettacolo.getAddress());
			
			preparedStatement.executeUpdate();
			
			preparedStatement2 = connection.prepareStatement(insertSQL2);
			preparedStatement2.setString(1, spettacolo.getShowId());
			preparedStatement2.setString(2, spettacolo.getHour());
			
			preparedStatement2.executeUpdate();
			
			connection.commit();
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (preparedStatement2 != null)
					preparedStatement2.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	@Override
	public void Aggiorna(ShowBean component) throws SQLException {
		
	}

	@Override
	public boolean Elimina(String id_spettacolo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ShowModel.TABLE_NAME + " WHERE id_spettacolo = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, id_spettacolo);

			result = preparedStatement.executeUpdate();
			
			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return (result != 0);
	}

	@Override
	public Collection<ShowBean> recuperaDatiDaAttributo(String attribute) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;

		Collection<ShowBean> spett = new ArrayList<ShowBean>();
		
		String selectSQL = "SELECT * FROM spettacolo WHERE indirizzo = ?";
		String selectSQLora = "SELECT orario FROM ora where id_spettacolo=?";
		ArrayList<String> spettacolo = new ArrayList<>();
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, attribute);
			
			System.out.println("recuperaDatiDaAttributo: "+ preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				ShowBean bean = new ShowBean();
				bean.setShowId(rs.getString("id_spettacolo"));
				bean.setTitleFilm(rs.getString("titolo_film"));
				bean.setDate(rs.getString("data_s"));
				bean.setAddress(rs.getString("indirizzo"));
				bean.setHall(rs.getInt("sala"));
				bean.setPrice(rs.getDouble("prezzo"));
				spettacolo.add(bean.getShowId());
				spett.add(bean);
			}
			
			
			for(String id: spettacolo) {
				preparedStatement2 = connection.prepareStatement(selectSQLora);
				preparedStatement2.setString(1, id);
				ResultSet rs2 = preparedStatement2.executeQuery();
				for(ShowBean b: spett)
					if(b.getShowId().equals(id)) {
						while(rs2.next()) {
							b.setHour(rs2.getString("orario"));
						}
					}
				
			}
			
			System.out.println(spett);
		} finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(preparedStatement2 != null) 
					preparedStatement2.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return spett;
	}
}
