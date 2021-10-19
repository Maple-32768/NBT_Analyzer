import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGIntArray extends TAGComponent{
	public static final int length_size = Integer.SIZE /Byte.SIZE;
	public static final int data_size = Integer.SIZE /Byte.SIZE;

	public TAGHeader header;
	public int length;
	public int[] value;

	public TAGIntArray(TAGHeader header, byte[] data) {
		this.header = header;
		this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getInt();
		value = new int[this.length];
		for (int i = 0; i < this.length; i++){
			value[i] = ByteBuffer.wrap(Arrays.copyOfRange(data, length_size + i * data_size, length_size + (i + 1) * data_size)).getInt();
			System.out.println(value[i]);
		}
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < this.length; i++){
			if (i != 0) result += '\u0020';
			result += String.valueOf(this.value[i]);
		}
		return result;
	}
}
