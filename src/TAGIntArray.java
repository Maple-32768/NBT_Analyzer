import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGIntArray extends TAGComponent{
	public static final int length_size = Integer.SIZE /Byte.SIZE;
	public static final int data_size = Integer.SIZE /Byte.SIZE;

	public TAGHeader header;
	public int length;
	public List<Integer> value;

	public TAGIntArray(TAGHeader header, byte[] data) {
		this.header = header;
		this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getInt();
		value = new ArrayList<>(this.length);
		for (int i = 0; i < this.length; i++){
			value.add(ByteBuffer.wrap(Arrays.copyOfRange(data, length_size + i * data_size, length_size + (i + 1) * data_size)).getInt());
		}
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
			if (i != 0 && i + 1 != this.length) result.append(",\u0020");
			result.append(this.value.get(i));
		}
		return result.append(']').toString();
	}
}
