import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TAGList extends TAGComponent{
    public static final int type_size = 1;
    public static final int length_size = Integer.SIZE / Byte.SIZE;

    public TAGHeader header;
    public byte type;
    public int length;
    public List<TAGComponent> value;
    public int size;

    public TAGList(TAGHeader header, byte[] data){
        this.header = header;
        this.type = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, type_size)).get();
        this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, type_size, type_size + length_size)).getInt();
        this.value = new ArrayList<>();
        this.size = type_size + length_size;
        byte[] data_temp = Arrays.copyOfRange(data, type_size + length_size, data.length);
        for (int i = 0; i < this.length; i++) {
            TAGComponent c;
            c = getNoHeaderComponent(this.type, data_temp);
            value.add(c);
            this.size += Objects.requireNonNull(c).getSize();
            data_temp = Arrays.copyOfRange(data_temp, c.getSize(), data_temp.length);
        }
    }

    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('[');
        for(int i = 0; i < this.value.size(); i++){
            TAGComponent c = this.value.get(i);
            if (i != 0) result.append(",\u0020");
            result.append(c.getHeader().tag_name != null ? c.getHeader().tag_name + " : " : "").append(c);
        }
        return result.append(']').toString();
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
