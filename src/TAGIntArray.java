import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGIntArray extends TAGComponent{
	private static final int length_size = Integer.SIZE /Byte.SIZE;
	private static final int data_size = Integer.SIZE /Byte.SIZE;

	public TAGHeader header;
	public int length;
	public List<Integer> value;
	public int size;

	public TAGIntArray(TAGHeader header, byte[] data) {
		this.header = header;
		this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getInt();
		this.size = 4 + data_size * this.length;
		value = new ArrayList<>();
		for (int i = 0; i < this.length; i++){
			value.add(ByteBuffer.wrap(Arrays.copyOfRange(data, length_size + i * data_size, length_size + (i + 1) * data_size)).getInt());
		}
	}

	public TAGIntArray(String name, @NotNull List<Integer> value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.length = value.size();
		this.size = length_size + data_size * this.length;
		this.value = new ArrayList<>();
		this.value.addAll(value);
	}

	public TAGIntArray(String name, int @NotNull [] value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.length = value.length;
		this.size = length_size + data_size * this.length;
		this.value = new ArrayList<>();
		for (int i : value) this.value.add(i);
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

	@Override
	public String toString(boolean json) {
		return this.toString();
	}

	@Override
	public byte getTypeId() {
		return 11;
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
