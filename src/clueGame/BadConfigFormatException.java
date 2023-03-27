package clueGame;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		// TODO Auto-generated constructor stub
		super("Config format file:  \n Status: bad (not usable)");
	}
	
	public BadConfigFormatException(String fileName) {
		// TODO Auto-generated constructor stub
		super(fileName + " Bad");
	}

	
}
