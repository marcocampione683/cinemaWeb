package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class LocalsModel implements CinemaModel<CinemaBean> {

	private static final String TABLE_NAME = "cinema";

	@Override
	public void AggiornaConChiave(CinemaBean updateCinema, String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + LocalsModel.TABLE_NAME + " SET" +
				" citta = ?,indirizzo = ?,nome = ?,regione = ?,mappa = ?,provincia = ? WHERE indirizzo = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);	
			
			preparedStatement.setString(1, updateCinema.getCity());
			preparedStatement.setString(2, updateCinema.getAddress());
			preparedStatement.setString(3, updateCinema.getName());
			preparedStatement.setString(4, updateCinema.getCountry());
			preparedStatement.setString(5, updateCinema.getMaps());
			preparedStatement.setString(6, updateCinema.getProvince());
			preparedStatement.setString(7, key);
			
			System.out.println("doUpdate: "+ preparedStatement.toString());
			preparedStatement.executeUpdate();
			
			connection.commit();
			
		} finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	@Override
	public CinemaBean recuperaDatiDaChiave(String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		CinemaBean bean = new CinemaBean();
		
		String selectSQL = "SELECT * FROM cinema WHERE indirizzo = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, key);
			
			System.out.println("recuperaDatiDaChiave: "+ preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				bean.setName(rs.getString("nome"));
				bean.setCity(rs.getString("citta"));
				bean.setProvince(rs.getString("provincia"));
				bean.setAddress(rs.getString("indirizzo"));
				bean.setCountry(rs.getString("regione"));
				bean.setMaps(rs.getString("mappa"));
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
	public Collection<CinemaBean> recuperaComponenti(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<CinemaBean> locals = new LinkedList<CinemaBean>();
		
		String selectSQL = "SELECT * FROM cinema";
		
		if(order!=null && !order.equals(""))
			selectSQL += "ORDER BY "+ order;
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			
			preparedStatement = connection.prepareStatement(selectSQL);
			
			System.out.println("recuperaComponenti: " + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				CinemaBean bean = new CinemaBean();
				
				bean.setName(rs.getString("nome"));
				bean.setCity(rs.getString("citta"));
				bean.setProvince(rs.getString("provincia"));
				bean.setAddress(rs.getString("indirizzo"));
				bean.setCountry(rs.getString("regione"));
				bean.setMaps(rs.getString("mappa"));
				
				locals.add(bean);
			}
		}finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			}
			finally {
				 DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return locals;
	}

	@Override
	public void Salva(CinemaBean cinema) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		String insertSQL = "INSERT INTO " + LocalsModel.TABLE_NAME
							+ " (citta,indirizzo,nome,regione,mappa,provincia)"
							+ " values(?,?,?,?,?,?)";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, cinema.getCity());
			preparedStatement.setString(2, cinema.getAddress());
			preparedStatement.setString(3, cinema.getName());
			preparedStatement.setString(4, cinema.getCountry());
			preparedStatement.setString(5, cinema.getMaps());
			preparedStatement.setString(6, cinema.getProvince());
			
			preparedStatement.executeUpdate();
				
			connection.commit();
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}

	@Override
	public void Aggiorna(CinemaBean component) throws SQLException {
		
		
	}

	@Override
	public boolean Elimina(String indirizzo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + LocalsModel.TABLE_NAME + " WHERE indirizzo = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, indirizzo);

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
	public Collection<CinemaBean> recuperaDatiDaAttributo(String attribute) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
