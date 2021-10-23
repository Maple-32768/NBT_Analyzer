import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGFloat extends TAGComponent{
	private static final int data_size = Float.SIZE / Byte.SIZE;

	public TAGHeader header;
	public float value;

	public TAGFloat(TAGHeader header, byte[] data) {
		this.header = header;
		this.value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getFloat();
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public String toString(boolean json) {
		return this.toString();
	}

	@Override
	public int getTypeId() {
		return 5;
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
