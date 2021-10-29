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

	public TAGLong(String name, long value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.value = value;
	}

	public TAGLong(String name){
		this(name, 0);
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
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
	public byte getTypeId() {
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

	@Override
	public byte[] getBytes() {
		byte[] header_bytes = this.header.getBytes(),
				value_bytes = this.getValueBytes(),
				result = new byte[getSize()];
		System.arraycopy(header_bytes, 0, result, 0, header_bytes.length);
		System.arraycopy(value_bytes, 0, result, header_bytes.length, value_bytes.length);
		return result;
	}

	@Override
	public byte[] getValueBytes() {
		return ByteBuffer.allocate(data_size).putLong(this.value).array();
	}
}
