import java.util.Arrays;

public abstract class TAGComponent {
    protected int size;
    protected TAGHeader header;

    abstract public TAGHeader getHeader();
    abstract public String toString();
    abstract public int getSize();

    public static TAGComponent Analyze(byte[] data){
        TAGComponent result = null;
        TAGHeader header = TAGHeader.getHeader(data);
        if(header.type == 0) return new TAGEnd(header);
        byte[] data_temp = Arrays.copyOfRange(data, header.size, data.length);
        switch (header.type){
            case 1:
                result = new TAGByte(header, data_temp);
                break;

            case 2:
                result = new TAGShort(header, data_temp);
                break;

            case 3:
                result = new TAGInt(header, data_temp);
                break;

            case 4:
                result = new TAGLong(header, data_temp);
                break;

            case 5:
                result = new TAGFloat(header, data_temp);
                break;

            case 6:
                result = new TAGDouble(header, data_temp);
                break;

            case 7:
                result = new TAGByteArray(header, data_temp);
                break;

            case 8:
                result = new TAGString(header, data_temp);
                break;

            case 9:
                //todo:tag_list
                break;

            case 10:
                result = new TAGCompound(header, data_temp);
                break;

            case 11:
                result = new TAGIntArray(header, data_temp);
                break;

            case 12:
                result = new TAGLongArray(header, data_temp);
                break;
        }
        return result;
    }
}
