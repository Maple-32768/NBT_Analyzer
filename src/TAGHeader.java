import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGHeader {
	public byte type;
	public short nameSize;
	public String tagName;
	public int length;

	public TAGHeader(byte[] data) {
		this.type = data[0];
		this.nameSize = ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 3)).getShort();
		if (nameSize != 0) {
			this.tagName = new String(Arrays.copyOfRange(data, 3, 4 + nameSize - 1));
		}
		this.length = 3 + nameSize;
	}

	public static TAGHeader getHeader(byte[] raw_data){
		return new TAGHeader(raw_data);
	}

}
