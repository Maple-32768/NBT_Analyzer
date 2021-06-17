
public class TAG_Byte {
	public Tag_Header header;
	byte value;

	public TAG_Byte(byte[] data) {
		int header_size = (data[1] << 8) + data[2] + 3;
		byte[] head = new byte[header_size];
		for(int i = 0; i < header_size; i++) {
			head[i] = data[i];
		}
		header = new Tag_Header(head);
		value = data[header_size + 1];
	}
}
