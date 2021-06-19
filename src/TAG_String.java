import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAG_String {
	public Tag_Header header;
	short value_size;
	String value;

	public TAG_String(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new Tag_Header(Arrays.copyOfRange(data, 0, header_size));
		value_size = ByteBuffer.wrap(Arrays.copyOfRange(data, header_size + 1, header_size + 3)).getShort();
		value = new String(Arrays.copyOfRange(data, header_size + 3, data.length));
	}
}
