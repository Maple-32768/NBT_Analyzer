import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGDouble extends TAGComponent{
	private static final int data_size = Double.SIZE / Byte.SIZE;

	public TAGHeader header;
	public double value;

	public TAGDouble(TAGHeader header, byte[] data) {
		this.header = header;
		value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getDouble();
	}
	public TAGDouble(String name, double value){
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
	public byte getTypeId() {
		return 6;
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
