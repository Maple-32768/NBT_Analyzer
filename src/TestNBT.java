

public class TestNBT {

	public static void main(String[] args) {
		byte[] data = {(byte)0x01,(byte)0x00,(byte)0x01,(byte)0x6e,(byte)0x08};
		TAGByte b = new TAGByte(data);
		System.out.println(b.header.tagName + ':' + b.data);
	}

}
