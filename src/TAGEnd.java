public class TAGEnd extends TAGComponent{
    public static final byte TYPE_ID = 0;

    public TAGHeader header;

    public TAGEnd(TAGHeader header){
        this.header = header;
    }

    public TAGEnd(){
        this.header = TAGHeader.getInstance(0, "");
    }

    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String toString(boolean json) {
        return "";
    }

    @Override
    public int getSize() {
        return this.header.size;
    }

    @Override
    public int getValueSize() {
        return 0;
    }

    @Override
    public byte getTypeId() {
        return TYPE_ID;
    }

    @Override
    public byte[] getBytes() {
        return this.header.getBytes();
    }

    @Override
    public byte[] getValueBytes() {
        return new byte[0];
    }
}
