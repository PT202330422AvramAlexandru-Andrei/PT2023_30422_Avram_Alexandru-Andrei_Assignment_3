package start;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import model.Client;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {

		Client client = new Client("dummy name", "dummy address", "dummy@address.co", 18);

		ClientBLL clientBLL = new ClientBLL();
		int id = clientBLL.insertClient(client);
		if (id > 0) {
			clientBLL.findClientById(id);
		}
		
		
		// Generate error
		try {
			clientBLL.findClientById(1);
		} catch (Exception ex) {
			LOGGER.log(Level.INFO, ex.getMessage());
		}
		
		
		//obtain field-value pairs for object through reflection
		ReflectionExample.retrieveProperties(client);
	}
	
	

}
