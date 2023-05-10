package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {

	private List<Validator<Client>> validators;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());
	}

	public Client findClientById(int id) {
		Client st = ClientDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return st;
	}

	public int insertClient(Client client) {
		for (Validator<Client> v : validators) {
			v.validate(client);
		}
		return ClientDAO.insert(client);
	}

	public int deleteClient(int id) {
		return ClientDAO.delete(id);
	}

	public int updateClient(int client, String name, String address, String email, int age) {
		for (Validator<Client> v : validators) {
			v.validate(findClientById(client));
		}
		return ClientDAO.update(client, name, address, email, age);
	}

	public void showAll() {
		ClientDAO.showAll();
	}

	public List<Client> selectAll() {
		return ClientDAO.selectAll();
	}

	public int countClients() {
		return ClientDAO.countAll();
	}
}
