import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGLong extends TAGComponent{
	private static final int data_size = Long.SIZE / Byte.SIZE;

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
	public String toString(boolean json) {
		return this.toString();
	}


	@Override
	public int getTypeId() {
		return 4;
	}

	@Override
	public int getSize() {
		return this.header.size + data_size;
	}

	@Override
	public int getValueSize() {
		return data_size;
	}
}
