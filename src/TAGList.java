import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGList extends TAGComponent{
    public static final int type_size = 1;
    public static final int length_size = Integer.SIZE / Byte.SIZE;

    public TAGHeader header;
    public byte type;
    public int data_size;
    public int length;
    public List<TAGComponent> value;
    public int size;

    public TAGList(TAGHeader header, byte[] data){
        this.header = header;
        this.type = data[0];
        this.data_size = TAGComponent.getLength(this.type);
        if(data_size == -1) throw new IllegalArgumentException();
        this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 1, 5)).getInt();
        this.size = 5 + this.data_size * this.length;
        this.value = new ArrayList<>();
        for (int i = 0; i < this.length; i++) {
            value.add(TAGComponent.getNoHeaderComponent(this.type, Arrays.copyOfRange(data, 5 + this.data_size * i, 5 + this.data_size * (i + 1))));
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
            if (i != 0 && i + 1 != this.value.size()) result.append(",\u0020");
            result.append(c.getHeader().tag_name != null ? c.getHeader().tag_name + " : " : "").append(c);
        }
        return result.append(']').toString();
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
