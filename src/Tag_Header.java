import java.nio.ByteBuffer;
import java.util.Arrays;

public class Tag_Header {
	public byte type;
	public short nameSize;
	public String tagName;

	public Tag_Header(byte[] data) {
		type = data[0];
		nameSize = ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		tagName = new String(Arrays.copyOfRange(data, 3, data.length));
	}

}
