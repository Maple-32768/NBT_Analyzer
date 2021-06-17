
public class Tag_Header {
	public byte type;
	public short nameSize;
	public String tagName;

	public Tag_Header(byte[] data) {
		type = data[0];
		System.out.println(type);
		nameSize = (short) ((data[1] << 8) + data[2]);
		System.out.println(nameSize);
		byte[] nameBytes = new byte[nameSize];
		for(int i = 0; i < (int)nameSize; i++) {
			nameBytes[i] = data[i + 3];
		}
		tagName = new String(nameBytes);

		System.out.println(tagName);

	}

}
