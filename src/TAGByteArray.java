import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGByteArray extends TAGComponent{
	public TAGHeader header;
	int length;
	byte[] value;

	public TAGByteArray(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new TAGHeader(Arrays.copyOfRange(data, 0, header_size));
		value = Arrays.copyOfRange(data, header_size + 5, data.length);
		length = value.length;
	}
}
