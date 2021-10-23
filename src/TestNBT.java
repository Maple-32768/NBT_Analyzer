public class TestNBT {

	public static void main(String[] args) {
		System.out.println(new TAGEnd().getTypeId());
		TAGHeader header = TAGHeader.getInstance(1, "root");
		System.out.println(header);
	}

}
