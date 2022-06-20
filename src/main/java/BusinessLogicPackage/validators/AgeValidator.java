package BusinessLogicPackage.validators;

import ModelPackage.Client;

/**
 * This class is a validator for the client age
 */

public class AgeValidator implements Validator<Client> {
	private static final int MIN_AGE = 7;
	private static final int MAX_AGE = 30;

	/**
	 * This method validates the age of the client.
	 * It will throw an exception if the client age is not in the specified age limit.
	 * @param t is the client witch will be validated.
	 */

	public void validate(Client t) {

		if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
			throw new IllegalArgumentException("The Client Age limit is not respected!");
		}

	}

}
