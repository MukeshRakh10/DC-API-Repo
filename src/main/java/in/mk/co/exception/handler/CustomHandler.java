package in.mk.co.exception.handler;

public class CustomHandler extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CustomHandler(){}
	 
	public CustomHandler(String msg){
		super(msg);
	}
}
