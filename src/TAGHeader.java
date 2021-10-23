import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TAGHeader {
	public static final int type_length = 1;
	public static final int name_size_length = Short.SIZE / Byte.SIZE;

	public byte type;
	public short name_size;
	public String tag_name;
	public int size;

	public TAGHeader(byte @NotNull [] data) {
		this.type = data[0];
		if(this.type == 0x00) return;
		this.name_size = ByteBuffer.wrap(Arrays.copyOfRange(data, type_length, type_length + name_size_length)).getShort();
		if (this.name_size == 0) this.tag_name = null;
		else this.tag_name = new String(Arrays.copyOfRange(data, type_length + name_size_length, type_length + name_size_length + this.name_size));
		this.size = calculateSize();
	}

	public TAGHeader(byte type, String name){
		this.type = type;
		if(this.type == 0x00) return;
		this.tag_name = name;
		this.name_size = (short) name.getBytes(StandardCharsets.UTF_8).length;
		this.size = this.calculateSize();
	}

	public int calculateSize(){
		return this.size = 3 + this.name_size;
	}

	public String getTagName(boolean json){
		String result = this.tag_name;
		if (json) result = '\"' + result + '\"';
		return result;
	}

	public static @NotNull TAGHeader getInstance(byte type, @NotNull String name){
		return new TAGHeader(type, name);
	}

	@Contract("_ -> new")
	public static @NotNull TAGHeader getHeader(byte[] raw_data){
		return new TAGHeader(raw_data);
	}

	public static @NotNull
	TAGHeader getNullHeader(byte type){
		TAGHeader result = new TAGHeader(new byte[] {(byte)0x00});
		result.type = type;
		return result;
	}

}
