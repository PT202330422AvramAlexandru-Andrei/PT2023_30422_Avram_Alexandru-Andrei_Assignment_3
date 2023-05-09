package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.AgeValidator;
import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {

	private List<Validator<Client>> validators;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());
		validators.add(new AgeValidator());
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
}
