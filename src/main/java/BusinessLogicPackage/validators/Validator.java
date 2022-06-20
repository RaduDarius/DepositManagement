package BusinessLogicPackage.validators;

/**
 * This interface contains the validate method.
 * Every validator will implement this method.
 * @param <T> is the Class which will be validated
 */

public interface Validator<T> {

	void validate(T t);
}
