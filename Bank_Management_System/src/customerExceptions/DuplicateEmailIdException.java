package customerExceptions;

public class DuplicateEmailIdException extends RuntimeException{

	@Override
	public String toString() {
		return getClass()+"DuplicateEmailIdException";
	}
}
