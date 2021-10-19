import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGByte extends TAGComponent{
	public static final int data_size = Byte.SIZE;

	public TAGHeader header;
	byte value;

	public TAGByte(TAGHeader header, byte[] data) {
		this.header = header;
		this.value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).get();
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
