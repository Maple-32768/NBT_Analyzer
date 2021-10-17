

public class TestNBT {

	public static void main(String[] args) {
		// data << n : 127
		byte[] data = {(byte)0x01,(byte)0x00,(byte)0x01,(byte)0x6e,(byte)0x7f};
		TAGComponent c = Analyze(data);
		System.out.println(c.getHeader().tagName + " : " + c);
	}

	public static TAGComponent Analyze(byte[] data){
		TAGComponent result = null;
		TAGHeader header = TAGHeader.getHeader(data);
		switch (header.type){
			case 0:
				break;

			case 1:
				result = new TAGByte(header, data[header.length]);
				break;
		}
		return result;
	}

}
