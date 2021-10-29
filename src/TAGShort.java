import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGShort extends TAGComponent{
	private static final int data_size = Short.SIZE / Byte.SIZE;

	public TAGHeader header;
	public short value;

	public TAGShort(TAGHeader header, byte[] data) {
		this.header = header;
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getShort();
	}

	public TAGShort(String name, short value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.value = value;
	}

	public TAGShort(String name){
		this(name, (short) 0);
	}

	public void setValue(short value) {
		this.value = value;
	}

	public short getValue() {
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
		return 2;
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
		return ByteBuffer.allocate(data_size).putShort(this.value).array();
	}
}
