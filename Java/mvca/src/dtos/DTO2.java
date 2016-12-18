package dtos;

public class DTO2 {

	private static final String name = "I am DTO2";

	public DTO2() {
		System.out.println("DTO2 has been newed up");
	}

	public String getMyName() {
		return name;
	}
}
