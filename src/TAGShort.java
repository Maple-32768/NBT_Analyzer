import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
}
