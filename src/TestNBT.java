public class TestNBT {

	public static void main(String[] args) {
		TAGHeader header = TAGHeader.getInstance(1, "root");
		System.out.println(header);
	}

}
