import java.util.Arrays;

public class TestNBT {

	public static void main(String[] args) {
		//            type       ,name_length          ,name      ,value
        //            2          ,01                   ,110(n)    ,数字
		byte[] data = {(byte)0x0b,(byte)0x00,(byte)0x01,(byte)0x6e,
				(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x05,
				(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
				(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,
				(byte)0x7f,(byte)0xff,(byte)0xff,(byte)0xff,
				(byte)0x80,(byte)0x00,(byte)0x00,(byte)0x00,
				(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff
		};
		TAGComponent c = Analyze(data);
		System.out.println(c.getHeader().tagName + " : " + c);
	}

	public static TAGComponent Analyze(byte[] data){
		TAGComponent result = null;
		TAGHeader header = TAGHeader.getHeader(data);
		byte[] data1 = Arrays.copyOfRange(data, header.size, data.length);
		switch (header.type){
			case 0:
				//todo:tag_end
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

			case 7:
				result = new TAGByteArray(header, data1);
				break;

			case 8:
				result = new TAGString(header, data1);
				break;

			case 9:
				//todo:tag_list
				break;

			case 10:
				//todo:tag_compound
				break;

			case 11:
				result = new TAGIntArray(header, data1);
				break;

		}
		return result;
	}

}
