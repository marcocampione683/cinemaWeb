package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;


public class FilmModel implements CinemaModel<FilmBean> {
	
	private static final String TABLE_NAME = "film";
	@Override
	public FilmBean recuperaDatiDaChiave(String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		FilmBean bean = new FilmBean();
		
		String selectSQL = "SELECT * FROM film WHERE titolo_film = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, key);
			
			System.out.println("recuperaDatiDaChiave: "+ preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				bean.setTitle(rs.getString("titolo_film"));
				bean.setType(rs.getString("genere"));
				bean.setDuration(rs.getString("durata"));
				bean.setDirectedBy(rs.getString("regia"));
				bean.setStory(rs.getString("trama"));
				bean.setPhoto(rs.getString("locandina"));
				bean.setVideo(rs.getString("cod_video"));
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
	public Collection<FilmBean> recuperaComponenti(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<FilmBean> films = new LinkedList<FilmBean>();
		
		String selectSQL = "SELECT * FROM film";
		
		if(order!=null && !order.equals(""))
			selectSQL += "ORDER BY "+ order;
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			
			preparedStatement = connection.prepareStatement(selectSQL);
			
			System.out.println("recuperaComponenti: " + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				FilmBean bean = new FilmBean();
				
				bean.setTitle(rs.getString("titolo_film"));
				bean.setType(rs.getString("genere"));
				bean.setDuration(rs.getString("durata"));
				bean.setDirectedBy(rs.getString("regia"));
				bean.setStory(rs.getString("trama"));
				bean.setPhoto(rs.getString("locandina"));
				bean.setVideo(rs.getString("cod_video"));
				
				films.add(bean);
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
		return films;
	}

	@Override
	public void Salva(FilmBean film) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + FilmModel.TABLE_NAME
							+ " (titolo_film,genere,durata,regia,trama,locandina,cod_video)"
							+ " values(?,?,?,?,?,?,?)";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, film.getTitle());
			preparedStatement.setString(2, film.getType());
			preparedStatement.setString(3, film.getDuration());
			preparedStatement.setString(4, film.getDirectedBy());
			preparedStatement.setString(5, film.getStory());
			preparedStatement.setString(6, film.getPhoto());
			preparedStatement.setString(7, film.getVideo());
			
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
	public void AggiornaConChiave(FilmBean updateFilm, String key) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE " + FilmModel.TABLE_NAME + " SET" +
				" titolo_film = ?,genere = ?,durata = ?,regia = ?,trama = ?,locandina = ?,cod_video = ? WHERE titolo_film = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);	
			
			preparedStatement.setString(1, updateFilm.getTitle());
			preparedStatement.setString(2, updateFilm.getType());
			preparedStatement.setString(3, updateFilm.getDuration());
			preparedStatement.setString(4, updateFilm.getDirectedBy());
			preparedStatement.setString(5, updateFilm.getStory());
			preparedStatement.setString(6, updateFilm.getPhoto());
			preparedStatement.setString(7, updateFilm.getVideo());
			preparedStatement.setString(8, key);
			
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
	public void Aggiorna(FilmBean updateFilm) throws SQLException {
	
	}

	@Override
	public boolean Elimina(String titolo_film) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + FilmModel.TABLE_NAME + " WHERE titolo_film = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, titolo_film);

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
	public Collection<FilmBean> recuperaDatiDaAttributo(String attribute) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
