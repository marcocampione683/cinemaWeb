package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class AccessModel implements CinemaModel<AccessBean> {
	
	private static final String TABLE_NAME = "accede";
	
	@Override
	public Collection<AccessBean> recuperaDatiDaAttributo(String attribute) throws SQLException {
		
		return null;
	}

	@Override
	public AccessBean recuperaDatiDaChiave(String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		AccessBean bean = new AccessBean();
		
		String selectSQL = "SELECT * FROM accede WHERE email = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, key);
			
			System.out.println("recuperaDatiDaChiave: "+ preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				bean.setEmail(rs.getString("email"));
				bean.setId_spettacolo(rs.getString("id_spettacolo"));
				bean.setM_accessi(rs.getString("m_accesso"));
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
	public Collection<AccessBean> recuperaComponenti(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<AccessBean> spettPrenotati = new LinkedList<AccessBean>();
		
		String selectSQL = "SELECT * FROM accede";
		
		if(order!=null && !order.equals(""))
			selectSQL += "ORDER BY "+ order;
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			
			preparedStatement = connection.prepareStatement(selectSQL);
			
			System.out.println("recuperaComponenti: " + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				AccessBean bean = new AccessBean();
				
				bean.setEmail(rs.getString("email"));
				bean.setId_spettacolo(rs.getString("id_spettacolo"));
				bean.setM_accessi(rs.getString("m_accesso"));
				
				spettPrenotati.add(bean);
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
		return spettPrenotati;
	}

	@Override
	public void Salva(AccessBean prenotazione) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + AccessModel.TABLE_NAME
							+ " (email,id_spettacolo,m_accesso)"
							+ " values(?,?,?)";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, prenotazione.getEmail());
			preparedStatement.setString(2, prenotazione.getId_spettacolo());
			preparedStatement.setString(3, prenotazione.getM_accessi());
			
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
	public void Aggiorna(AccessBean component) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean Elimina(String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + AccessModel.TABLE_NAME + " WHERE id_spettacolo = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, key);

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
	public void AggiornaConChiave(AccessBean component, String key) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
