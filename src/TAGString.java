import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TAGString extends TAGComponent{
	private static final int length_size = Short.SIZE /Byte.SIZE;
	private static final int data_size = 1;

	public TAGHeader header;
	public short length;
	public String value;
	public int size;

	public TAGString(TAGHeader header, byte[] data) {
		this.header = header;
		this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getShort();
		this.size = length_size + data_size * this.length;
		this.value = new String(Arrays.copyOfRange(data, length_size, length_size + length * data_size));
	}

	public TAGString(String name, String value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.length = (short) value.length();
		this.size = length_size + data_size * this.length;
		this.value = value;
	}

	public TAGString(String name){
		this(name, "");
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		return '\"' + this.value + '\"';
	}

	@Override
	public String toString(boolean json) {
		return this.toString();
	}

	@Override
	public byte getTypeId() {
		return 8;
	}

	@Override
	public int getSize() {
		return this.header.size + this.size;
	}

	@Override
	public int getValueSize() {
		return this.size;
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
		byte[] length_bytes = ByteBuffer.allocate(length_size).putShort(this.length).array(),
				value_bytes = this.value.getBytes(StandardCharsets.UTF_8),
				result = new byte[length_size + data_size * length];
		System.arraycopy(length_bytes, 0, result, 0, length_bytes.length);
		System.arraycopy(value_bytes, 0, result, length_bytes.length, value_bytes.length);
		return result;
	}
}
