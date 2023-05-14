package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

public class ClientDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO client (name,address,email,age)" + " VALUES (?,?,?,?)";
	private final static String findStatementString = "SELECT * FROM client where id = ?";
	private final static String deleteStatementString = "DELETE FROM client WHERE id = ?";
	private final static String updateStatementString = "UPDATE client SET name = ?, address = ?, email = ?, age = ? WHERE id = ?";
	private final static String selectAllStatementString = "SELECT * FROM client";

	/*public static Client findById(int clientId) {
		Client toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setLong(1, clientId);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String address = rs.getString("address");
			String email = rs.getString("email");
			int age = rs.getInt("age");
			toReturn = new Client(clientId, name, address, email, age);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}*/

	/*public static int delete(int clientId) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int deletedId = -1;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, clientId);
			deleteStatement.executeUpdate();

			ResultSet rs = deleteStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return deletedId;
	}*/

	public static int update(int client_id, String name, String address, String email, int age) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(1, name);
			updateStatement.setString(2, address);
			updateStatement.setString(3, email);
			updateStatement.setInt(4, age);
			updateStatement.setInt(5, client_id);
			updateStatement.executeUpdate();

			ResultSet rs = updateStatement.getGeneratedKeys();
			if (rs.next()) {
				client_id = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return client_id;
	}

	/*public static int insert(Client client) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, client.getName());
			insertStatement.setString(2, client.getAddress());
			insertStatement.setString(3, client.getEmail());
			insertStatement.setInt(4, client.getAge());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}*/

	public static void showAll() {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement selectAllStatement = null;
		try {
			selectAllStatement = dbConnection.prepareStatement(selectAllStatementString);
			ResultSet rs = selectAllStatement.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				int age = rs.getInt("age");
				System.out.println("Client: " + id + " " + name + " " + address + " " + email + " " + age);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:selectAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(selectAllStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	/*public static List<Client> selectAll() {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement selectAllStatement = null;
		List<Client> clients = new ArrayList<Client>();
		try {
			selectAllStatement = dbConnection.prepareStatement(selectAllStatementString);
			ResultSet rs = selectAllStatement.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				int age = rs.getInt("age");
				clients.add(new Client(id, name, address, email, age));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:selectAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(selectAllStatement);
			ConnectionFactory.close(dbConnection);
		}
		return clients;
	}*/

	public static int countAll() {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement selectAllStatement = null;
		int count = 0;
		try {
			selectAllStatement = dbConnection.prepareStatement(selectAllStatementString);
			ResultSet rs = selectAllStatement.executeQuery();
			while(rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:countAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(selectAllStatement);
			ConnectionFactory.close(dbConnection);
		}
		return count;
	}
}
