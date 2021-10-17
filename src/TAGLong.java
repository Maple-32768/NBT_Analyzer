import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGLong {
	public TAGHeader header;
	long value;

	public TAGLong(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new TAGHeader(Arrays.copyOfRange(data, 0, header_size));
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, header_size + 1, data.length)).getLong();
	}
}
