import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGShort extends TAGComponent{
	public static final int data_size = Short.SIZE / Byte.SIZE;

	public TAGHeader header;
	public short value;

	public TAGShort(TAGHeader header, byte[] data) {
		this.header = header;
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getShort();
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
