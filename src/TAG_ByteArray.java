import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAG_ByteArray {
	public Tag_Header header;
	int length;
	byte[] value;

	public TAG_ByteArray(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new Tag_Header(Arrays.copyOfRange(data, 0, header_size));
		value = Arrays.copyOfRange(data, header_size + 5, data.length);
		length = value.length;
	}
}
