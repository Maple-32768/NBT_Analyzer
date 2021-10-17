public class TAGByte extends TAGComponent{
	public TAGHeader header;
	byte data;

	public TAGByte(TAGHeader header, byte data) {
		this.header = header;
		this.data = data;
	}

	@Override
	public TAGHeader getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		return String.valueOf(this.data);
	}
}
