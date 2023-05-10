package start;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import model.Client;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {

		/*Client client = new Client("dummy name", "dummy address", "dummy@address.co", 18);

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
		ReflectionExample.retrieveProperties(client);*/

		Scanner scanner = new Scanner(System.in);
		ClientBLL clientBLL = new ClientBLL();
		int option = 0;

		System.out.println("Choose what you want to do: ");
		System.out.println("[1] Add New Client");
		System.out.println("[2] Edit Client");
		System.out.println("[3] Delete Client");
		System.out.println("[4] View Clients");

		option = scanner.nextInt();

		switch (option) {
		case 1:
			System.out.println("~~~~~~~~~~~~~~~~~~\n~ Add New Client ~\n~~~~~~~~~~~~~~~~~~");
			System.out.println("Name: ");
			String name = scanner.next();
			System.out.println("Address: ");
			String address = scanner.next();
			System.out.println("Email: ");
			String email = scanner.next();
			System.out.println("Age: ");
			int age = scanner.nextInt();
			Client client = new Client(name, address, email, age);
			clientBLL.insertClient(client);
			System.out.println();
			break;
		case 2:
			System.out.println("~~~~~~~~~~~~~~~\n~ Edit Client ~\n~~~~~~~~~~~~~~~");
			System.out.println("\n\n Which client do you want to edit? (enter id):\n\n");
			clientBLL.selectAll();
			int id = scanner.nextInt();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~\n~ What do you want to edit? ~\n~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("[1] Name");
			System.out.println("[2] Address");
			System.out.println("[3] Email");
			System.out.println("[4] Age");
			int option2 = scanner.nextInt();
			switch (option2) {
				case 1:
					System.out.println("New Name: ");
					String name1 = scanner.next();

					clientBLL.updateClient(clientBLL.findClientById(id), name1, clientBLL.findClientById(id).getAddress(), clientBLL.findClientById(id).getEmail(), clientBLL.findClientById(id).getAge());
					break;
				case 2:
					System.out.println("New Address: ");
					String address1 = scanner.next();

					clientBLL.updateClient(clientBLL.findClientById(id), clientBLL.findClientById(id).getName(), address1, clientBLL.findClientById(id).getEmail(), clientBLL.findClientById(id).getAge());
					break;
				case 3:
					System.out.println("New Email: ");
					String email1 = scanner.next();

					clientBLL.updateClient(clientBLL.findClientById(id), clientBLL.findClientById(id).getName(), clientBLL.findClientById(id).getAddress(), email1, clientBLL.findClientById(id).getAge());
					break;
				case 4:
					System.out.println("New Age: ");
					int age1 = scanner.nextInt();

					clientBLL.updateClient(clientBLL.findClientById(id), clientBLL.findClientById(id).getName(), clientBLL.findClientById(id).getAddress(), clientBLL.findClientById(id).getEmail(), age1);
					break;
			}

			System.out.println("\n\n");

			clientBLL.selectAll();
			break;
		case 3:
			System.out.println("~~~~~~~~~~~~~~~~~\n~ Delete Client ~\n~~~~~~~~~~~~~~~~~");
			System.out.println("\n\n Which client do you want to delete? (enter id):\n\n");
			clientBLL.selectAll();
			int id1 = scanner.nextInt();
			clientBLL.deleteClient(id1);
			System.out.println("\n\n");
			clientBLL.selectAll();
			break;
		case 4:
			System.out.println("~~~~~~~~~~~~~~~~\n~ View Clients ~\n~~~~~~~~~~~~~~~~");
			clientBLL.selectAll();
			break;
		}
	}
	
	

}
