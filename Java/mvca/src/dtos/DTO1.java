package dtos;

public class DTO1 {

	private static DTO1 instance;

	private static final String name = "I am DTO1";

	private DTO1() {
		System.out.println("DTO1 has been newed up");
	}

	public static synchronized DTO1 getInstance() {
		if (instance == null) {
			instance = new DTO1();
		}
		return instance;
	}

	public String getMyName() {
		return name;
	}
}
