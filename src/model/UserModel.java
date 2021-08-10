package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class UserModel implements CinemaModel<UserBean> {
	
	private static final String TABLE_NAME = "utente";

	@Override
	public UserBean recuperaDatiDaChiave(String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		UserBean bean = new UserBean();
		
		String selectSQL = "SELECT * FROM utente WHERE email = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, key);
			
			System.out.println("recuperaDatiDaChiave: "+ preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				bean.setName(rs.getString("nome"));
				bean.setSurname(rs.getString("cognome"));
				bean.setBirth(rs.getString("nascita"));
				bean.setEmail(rs.getString("email"));
				bean.setPassword(rs.getString("psw"));
				bean.setRole(rs.getString("ruolo"));
				bean.setRegistration(rs.getString("data_registraz"));
				bean.setAccessNumber(rs.getInt("n_accessi"));
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
	public Collection<UserBean> recuperaComponenti(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<UserBean> users = new LinkedList<UserBean>();
		
		String selectSQL = "SELECT * FROM utente";
		
		if(order!=null && !order.equals(""))
			selectSQL += "ORDER BY "+ order;
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			
			preparedStatement = connection.prepareStatement(selectSQL);
			
			System.out.println("recuperaComponenti: " + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				UserBean bean = new UserBean();
				
				bean.setName(rs.getString("nome"));
				bean.setSurname(rs.getString("cognome"));
				bean.setBirth(rs.getString("nascita"));
				bean.setEmail(rs.getString("email"));
				bean.setPassword(rs.getString("psw"));
				bean.setRole(rs.getString("ruolo"));
				bean.setRegistration(rs.getString("data_registraz"));
				bean.setAccessNumber(rs.getInt("n_accessi"));
				
				users.add(bean);
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
		return users;
	}

	@Override
	public void Salva(UserBean user) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + UserModel.TABLE_NAME
							+ " (nome,cognome,nascita,email,psw,data_registraz,ruolo,n_accessi)"
							+ " values(?,?,?,?,?,?,?,?)";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getBirth());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setString(6, user.getRegistration());
			preparedStatement.setString(7, user.getRole());
			preparedStatement.setInt(8, user.getAccessNumber());
			
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
	public boolean Elimina(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + UserModel.TABLE_NAME + " WHERE email = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, email);

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
	public void Aggiorna(UserBean utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + UserModel.TABLE_NAME + " SET" +
				" n_accessi = ? WHERE email = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);	
			
			preparedStatement.setInt(1, utente.getAccessNumber());
			preparedStatement.setString(2, utente.getEmail());
			
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
	public Collection<UserBean> recuperaDatiDaAttributo(String attribute) throws SQLException {
		return null;
	}

	@Override
	public void AggiornaConChiave(UserBean utente, String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + UserModel.TABLE_NAME + " SET" +
				" nome = ?,cognome = ?,nascita = ?,email = ?,psw = ? WHERE email = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);	
			
			preparedStatement.setString(1, utente.getName());
			preparedStatement.setString(2, utente.getSurname());
			preparedStatement.setString(3, utente.getBirth());
			preparedStatement.setString(4, utente.getEmail());
			preparedStatement.setString(5, utente.getPassword());
			preparedStatement.setString(6, key);
			
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
	
}
