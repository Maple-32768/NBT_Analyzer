import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGLong extends TAGComponent{
	public static final int data_size = Long.SIZE / Byte.SIZE;
	public static final int size = data_size;

	public TAGHeader header;
	public long value;

	public TAGLong(TAGHeader header, byte[] data) {
		this.header = header;
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getLong();
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
	public int getSize() {
		return size;
	}
}
