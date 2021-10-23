import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGByte extends TAGComponent{
	private static final int data_size = 1;

	public TAGHeader header;
	public byte value;

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
		return new String(new byte[] {this.value});
	}

	@Override
	public String toString(boolean json) {
		return this.toString();
	}

	@Override
	public int getSize() {
		return this.header.size + data_size;
	}

	@Override
	public int getValueSize() {
		return data_size;
	}

	@Override
	public int getTypeId() {
		return 1;
	}
}
