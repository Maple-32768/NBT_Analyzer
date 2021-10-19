import java.util.Arrays;
import java.nio.ByteBuffer;

public class TestNBT {

	public static void main(String[] args) {
		//            type       ,name_length          ,name      ,value
        //            2          ,01                   ,110(n)    ,数字
		byte[] data = {(byte)0x04,(byte)0x00,(byte)0x01,(byte)0x6e,(byte)0x7f,(byte)0xff,(byte)0xff,(byte)0xff,
                (byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
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

		}
		return result;
	}

}
