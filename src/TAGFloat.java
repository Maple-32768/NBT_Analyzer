import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGFloat extends TAGComponent{
	public static final int data_size = Float.SIZE / Byte.SIZE;
	public static final int size = data_size;

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
	public int getSize() {
		return size;
	}
}
