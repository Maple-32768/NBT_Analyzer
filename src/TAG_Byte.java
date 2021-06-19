import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAG_Byte {
	public Tag_Header header;
	byte value;

	public TAG_Byte(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new Tag_Header(Arrays.copyOfRange(data, 0, header_size));
		value = data[header_size + 1];
	}
}
