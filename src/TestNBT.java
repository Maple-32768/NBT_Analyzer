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
		TAGComponent c = TAGComponent.Analyze(data);
		System.out.println(c.getHeader().tagName + " : " + c);
	}

}
