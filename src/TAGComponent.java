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
                result = new TAGList(header, data_temp);
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

    public static int getLength(byte type){
        switch (type){
            case 0:
                return TAGEnd.size;

            case 1:
                return TAGByte.size;

            case 2:
                return TAGShort.size;

            case 3:
                return TAGInt.size;

            case 4:
                return TAGLong.size;

            case 5:
                return TAGFloat.size;

            case 6:
                return TAGDouble.size;

            case 7:

            case 8:

            case 9:

            case 10:

            case 11:

            case 12:

            default:
                return -1;

        }
    }

    public static TAGComponent getNoHeaderComponent(byte type, byte[] data){
        TAGHeader null_header = TAGHeader.getNullHeader(type);
        switch (type){
            case 0:
                return new TAGEnd(null_header);

            case 1:
                return new TAGByte(null_header, data);

            case 2:
                return new TAGShort(null_header, data);

            case 3:
                return new TAGInt(null_header, data);

            case 4:
                return new TAGLong(null_header, data);

            case 5:
                return new TAGFloat(null_header, data);

            case 6:
                return new TAGDouble(null_header, data);

            default:
                return null;
        }
    }
}
