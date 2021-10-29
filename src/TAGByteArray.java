import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGByteArray extends TAGComponent{

	private static final int length_size = Integer.SIZE /Byte.SIZE;
	private static final int data_size = 1;

	public TAGHeader header;
	public int length;
	public byte[] value;
	public int size;

	public TAGByteArray(TAGHeader header, byte[] data) {
		this.header = header;
		this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getInt();
		this.size = this.calculateSize();
		this.value = new byte[this.length];
		for (int i = 0; i < this.length; i++){
			this.value[i] = ByteBuffer.wrap(Arrays.copyOfRange(data, length_size + i * data_size, length_size + (i + 1) * data_size)).get();
		}
	}

	public TAGByteArray(String name, List<Byte> value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.length = value.size();
		this.size = this.calculateSize();
		this.value = new byte[this.length];
		for(int i = 0; i < this.length; i++) this.value[i] = value.get(i);
	}

	public TAGByteArray(String name, byte [] value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.length = value.length;
		this.size = this.calculateSize();
		this.value = value.clone();
	}

	private int calculateSize() {
		return length_size + data_size * this.length;
	}

	public TAGByteArray(String name){
		this(name, new ArrayList<>());
	}

	public void setValue(byte[] value) {
		this.length = value.length;
		this.value = value.clone();
		this.size = this.calculateSize();
	}

	public void setValue(List<Byte> value){
		byte[] value_array = new byte[value.size()];
		for (int i = 0; i < value.size(); i++) value_array[i] = value.get(i);
		this.setValue(value_array);
	}

	public byte[] getValue() {
		return this.value;
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append('[');
		for (int i = 0; i < this.length; i++){
			if (i != 0) result.append(",\u0020");
			result.append(String.format("%02x",this.value[i]));
		}
		return result.append(']').toString();
	}

	@Override
	public String toString(boolean json) {
		return this.toString();
	}

	@Override
	public byte getTypeId() {
		return 7;
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
		int values_size = getValueSize() - length_size;
		byte[] length_bytes = ByteBuffer.allocate(length_size).putInt(this.length).array(),
				values_bytes = new byte[values_size],
				result = new byte[getValueSize()];
		if (this.length >= 0) System.arraycopy(this.value, 0, values_bytes, 0, this.length);
		System.arraycopy(length_bytes, 0, result, 0 , length_size);
		System.arraycopy(values_bytes, 0, result, length_size, values_size);
		return result;
	}
}
