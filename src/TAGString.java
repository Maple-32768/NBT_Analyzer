import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
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

	public TAGString(String name, @NotNull String value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.length = (short) value.length();
		this.size = length_size + data_size * this.length;
		this.value = value;
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
}
