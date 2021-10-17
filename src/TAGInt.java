import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGInt {
	public TAGHeader header;
	int value;

	public TAGInt(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new TAGHeader(Arrays.copyOfRange(data, 0, header_size));
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, header_size + 1, data.length)).getInt();
	}
}
