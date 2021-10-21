import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TAGComponent {
    protected int size;
    protected TAGHeader header;

    abstract public TAGHeader getHeader();
    abstract public String toString();
    abstract public int getSize();

    public static final List<Integer> fixed_size_types = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
    public static final List<Integer> unfixed_size_types = new ArrayList<>(Arrays.asList(7, 8, 9, 10, 11, 12));

    public static TAGComponent Analyze(byte[] data){
        TAGComponent result = null;
        TAGHeader header = TAGHeader.getHeader(data);
        if(header.type == 0) return new TAGEnd(header);
        System.out.println(header.tag_name);
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

            default:

        }
        return result;
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

            case 7:
                return new TAGByteArray(null_header, data);

            case 8:
                return new TAGString(null_header, data);

            case 9:
                return new TAGList(null_header, data);

            case 10:
                return new TAGCompound(null_header, data);

            case 11:
                return new TAGIntArray(null_header, data);

            case 12:
                return new TAGLongArray(null_header, data);

        }
        return null;
    }
}
