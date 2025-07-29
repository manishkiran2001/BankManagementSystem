package customerExceptions;

public class InvalidCustomerDetailsException extends RuntimeException{

	private String exceptionMsg;
	
	public InvalidCustomerDetailsException() {
	}

	public InvalidCustomerDetailsException(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	@Override
	public String toString() {
		return "InvalidCustomerDetailsException [exceptionMsg=" + exceptionMsg + "]";
	}
}
