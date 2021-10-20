import java.util.Arrays;

public abstract class TAGComponent {
    abstract public TAGHeader getHeader();
    abstract public String toString();

    public static TAGComponent Analyze(byte[] data){
        TAGComponent result = null;
        TAGHeader header = TAGHeader.getHeader(data);
        byte[] data1 = Arrays.copyOfRange(data, header.length, data.length);
        switch (header.type){
            case 0:
                //todo:tag_end
                break;

            case 1:
                result = new TAGByte(header, data1);
                break;

            case 2:
                result = new TAGShort(header, data1);
                break;

            case 3:
                result = new TAGInt(header, data1);
                break;

            case 4:
                result = new TAGLong(header, data1);
                break;

            case 5:
                result = new TAGFloat(header, data1);
                break;

            case 6:
                result = new TAGDouble(header, data1);
                break;

            case 7:
                result = new TAGByteArray(header, data1);
                break;

            case 8:
                result = new TAGString(header, data1);
                break;

            case 9:
                //todo:tag_list
                break;

            case 10:
                //todo:tag_compound
                break;

            case 11:
                result = new TAGIntArray(header, data1);
                break;

        }
        return result;
    }
}
