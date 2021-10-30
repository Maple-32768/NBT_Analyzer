import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGByte extends TAGComponent{
	public static final byte TYPE_ID = 1;

	private static final int data_size = 1;

	public TAGHeader header;
	public byte value;

	public TAGByte(TAGHeader header, byte[] data) {
		this.header = header;
		this.value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).get();
	}

	public TAGByte(String name, byte value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.value = value;
	}

	public TAGByte(String name){
		this(name, (byte)0);
	}

	public void setValue(byte value){
		this.value = value;
	}

	public byte getValue() {
		return this.value;
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
	public byte getTypeId() {
		return TYPE_ID;
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
		return ByteBuffer.allocate(data_size).put(this.value).array();
	}
}
