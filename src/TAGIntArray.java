import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGIntArray {
	public TAGHeader header;
	int length;
	int[] value;

	public TAGIntArray(byte[] data) {
		int header_size = 3 + ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 2)).getShort();
		header = new TAGHeader(Arrays.copyOfRange(data, 0, header_size));
		length = ByteBuffer.wrap(Arrays.copyOfRange(data, header_size + 1, header_size + 5)).getInt();
		value = new int[length];
		for(int i = header_size + 5; i < data.length; i += 4) {
			value[i] = ByteBuffer.wrap(Arrays.copyOfRange(data, i, i + 3)).getInt();
		}
	}
}
