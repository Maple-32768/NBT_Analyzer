import java.util.Arrays;
import java.nio.ByteBuffer;

public class TestNBT {

	public static void main(String[] args) {
		//            type       ,name_length          ,name      ,value
        //            2          ,01                   ,110(n)    ,数字
		byte[] data = {(byte)0x06,(byte)0x00,(byte)0x01,(byte)0x6e,(byte)0x41,(byte)0x61,(byte)0xfd,(byte)0x30,
                (byte)0xc9,(byte)0xba,(byte)0x5e,(byte)0x35};
		TAGComponent c = Analyze(data);
		System.out.println(c.getHeader().tagName + " : " + c);
	}

	public static TAGComponent Analyze(byte[] data){
		TAGComponent result = null;
		TAGHeader header = TAGHeader.getHeader(data);
		byte[] data1 = Arrays.copyOfRange(data, header.length, data.length);
		switch (header.type){
			case 0:
				break;

			case 1:
				result = new TAGByte(header, data1);
				break;

			case 2:
				result = new TAGShort(header, data1);
                break;

            case 3:
                result = new TAGInt(header, data1);
                break;

            case 4:
                result = new TAGLong(header, data1);
                break;

			case 5:
				result = new TAGFloat(header, data1);
				break;

			case 6:
				result = new TAGDouble(header, data1);
				break;

		}
		return result;
	}

}
