import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAG_Double {
	public Tag_Header header;
	double value;

	public TAG_Double(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new Tag_Header(Arrays.copyOfRange(data, 0, header_size));
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, header_size + 1, data.length)).getDouble();
	}
}
