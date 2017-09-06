package cinspect.exceptions;

public class UnimplementedFunctionException extends Exception {

	/**
	 * Keep eclipse from giving a warning. Ignore this. 
	 */
	private static final long serialVersionUID = -247921974850590550L;

	public UnimplementedFunctionException() { super(); }
	public UnimplementedFunctionException(String msg) { super(msg); }
	public UnimplementedFunctionException(String msg, Throwable cause) { super(msg, cause); }
	public UnimplementedFunctionException(Throwable cause) { super(cause); }
}

/*
public class FooException extends Exception {
	  public FooException() { super(); }
	  public FooException(String message) { super(message); }
	  public FooException(String message, Throwable cause) { super(message, cause); }
	  public FooException(Throwable cause) { super(cause); }
	}
*/