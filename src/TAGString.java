import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGString extends TAGComponent{
	public static final int length_size = Short.SIZE /Byte.SIZE;
	public static final int data_size = 1;

	public TAGHeader header;
	public short length;
	public String value;
	public int size;

	public TAGString(TAGHeader header, byte[] data) {
		this.header = header;
		this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getShort();
		this.size = data_size * this.length;
		value = new String(Arrays.copyOfRange(data, length_size, length_size + length * data_size));
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public int getSize() {
		return this.size;
	}
}
