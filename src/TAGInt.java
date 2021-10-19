import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGInt extends TAGComponent{
	public static final int data_size = 4;

	public TAGHeader header;
	int value;

	public TAGInt(TAGHeader header, byte[] data) {
		this.header = header;
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getInt();
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
