import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGInt extends TAGComponent{
	public static final int data_size = Integer.SIZE / Byte.SIZE;
	public static final int size = data_size;

	public TAGHeader header;
	public int value;

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

	@Override
	public String toString(boolean json) {
		return this.toString();
	}

	@Override
	public int getSize() {
		return size;
	}
}
