import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGInt extends TAGComponent{
	private static final int data_size = Integer.SIZE / Byte.SIZE;

	public TAGHeader header;
	public int value;

	public TAGInt(TAGHeader header, byte[] data) {
		this.header = header;
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getInt();
	}

	public TAGInt(String name, int value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.value = value;
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
		return 3;
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
