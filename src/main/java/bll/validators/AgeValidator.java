package bll.validators;

import model.Client;

public class AgeValidator implements Validator<Client> {
	private static final int LEGAL_AGE = 18;

	public void validate(Client t) {

		if (t.getAge() < LEGAL_AGE) {
			throw new IllegalArgumentException("The Client is too young to buy some products!");
		}

	}

}
