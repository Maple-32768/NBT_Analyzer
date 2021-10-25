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
            this.size += Objects.requireNonNull(c).getValueSize();
            data_temp = Arrays.copyOfRange(data_temp, c.getSize(), data_temp.length);
        }
    }

    public TAGList(String name, byte type, List<TAGComponent> value){
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.type = type;
        this.length = value.size();
        this.size = type_size + length_size;
        this.value = new ArrayList<>();
        for(TAGComponent c : value) {
            this.value.add(c);
            this.size += c.getValueSize();
        }
    }

    public TAGList(String name, int type, List<TAGComponent> value){
        this(name, (byte)type, value);
    }

    public TAGList(String name, byte type, TAGComponent [] value){
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.type = type;
        this.length = value.length;
        this.size = type_size + length_size;
        this.value = new ArrayList<>();
        for(TAGComponent c : value) {
            this.value.add(c);
            this.size += c.getValueSize();
        }
    }

    public TAGList(String name){
        this(name, -1, new ArrayList<>());
    }

    public TAGList(String name, int type, TAGComponent [] value){
        this(name, (byte)type, value);
    }

    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    public String toString(boolean json) {
        StringBuilder result = new StringBuilder();
        result.append('[');
        for(int i = 0; i < this.value.size(); i++){
            TAGComponent c = this.value.get(i);
            if (i != 0) result.append(",\u0020");
            result.append(c.getHeader()).append(c.toString(json));
        }
        return result.append(']').toString();
    }

    @Override
    public String toString(){
        return this.toString(false);
    }

    @Override
    public byte getTypeId() {
        return 9;
    }

    @Override
    public int getSize() {
        return this.header.size + this.size;
    }

    @Override
    public int getValueSize() {
        return this.size;
    }

    @Override
    public byte[] getBytes() {
        byte[] header_bytes = this.header.getBytes(),
                value_bytes = this.getValueBytes(),
                result = new byte[getSize()];
        System.arraycopy(header_bytes, 0, result, 0, header_bytes.length);
        System.arraycopy(value_bytes, 0, result, header_bytes.length, value_bytes.length);
        return result;
    }

    @Override
    public byte[] getValueBytes() {
        int valueSize = this.getValueSize();
        int prefix_length = type_size + length_size;
        byte[] type_bytes = ByteBuffer.allocate(type_size).put(this.type).array(),
                length_bytes = ByteBuffer.allocate(length_size).putInt(this.length).array(),
                values_bytes = new byte[valueSize - prefix_length],
                result = new byte[valueSize];
        int sum = 0;
        for (TAGComponent c : this.value) {
            byte[] value_bytes = c.getValueBytes();
            System.arraycopy(value_bytes, 0, values_bytes, sum, value_bytes.length);
            sum += c.getValueSize();
        }
        System.arraycopy(type_bytes, 0, result, 0, type_size);
        System.arraycopy(length_bytes, 0, result, type_size, length_size);
        System.arraycopy(values_bytes, 0, result, prefix_length, valueSize - prefix_length);
        return result;
    }
}
