import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGHeader {
	public byte type;
	public short name_size;
	public String tag_name;
	public int size;

	public TAGHeader(byte[] data) {
		this.type = data[0];
		if(this.type == 0x00) return;
		this.name_size = ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 3)).getShort();
		if (name_size == 0) this.tag_name = null;
		else this.tag_name = new String(Arrays.copyOfRange(data, 3, 4 + name_size - 1));
		this.size = 3 + name_size;
	}

	public static TAGHeader getHeader(byte[] raw_data){
		return new TAGHeader(raw_data);
	}

	public static TAGHeader getNullHeader(byte type){
		TAGHeader result = new TAGHeader(new byte[] {(byte)0x00});
		result.type = type;
		return result;
	}

}
