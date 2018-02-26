package Data;

public class TableData1 {
	public int number;
	public String Username;
	public int LineNumber;

	public TableData1(int number, String username, int lineNumber) {
		super();
		this.number = number;
		Username = username;
		LineNumber = lineNumber;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public int getLineNumber() {
		return LineNumber;
	}
	public void setLineNumber(int lineNumber) {
		LineNumber = lineNumber;
	}

}
