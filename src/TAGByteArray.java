import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGByteArray extends TAGComponent{

	public static final int length_size = Integer.SIZE /Byte.SIZE;
	public static final int data_size = 1;

	public TAGHeader header;
	int length;
	byte[] value;

	public TAGByteArray(TAGHeader header, byte[] data) {
		this.header = header;
		this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getInt();
		value = new byte[this.length];
		for (int i = 0; i < this.length; i++){
			value[i] = ByteBuffer.wrap(Arrays.copyOfRange(data, i + length_size, i +length_size + data_size)).get();
		}
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		return new String(this.value);
	}
}
