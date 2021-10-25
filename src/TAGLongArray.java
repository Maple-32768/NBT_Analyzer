import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGLongArray extends TAGComponent{
    private static final int length_size = Long.SIZE /Byte.SIZE;
    private static final int data_size = Long.SIZE /Byte.SIZE;

    public TAGHeader header;
    public int length;
    public List<Long> value;
    public int size;

    public TAGLongArray(TAGHeader header, byte[] data){
        this.header = header;
        this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getInt();
        this.size = calculateSize();
        value = new ArrayList<>();
        for (int i = 0; i < this.length; i++){
            value.add(ByteBuffer.wrap(Arrays.copyOfRange(data, length_size + i * data_size, length_size + (i + 1) * data_size)).getLong());
        }
    }

    public TAGLongArray(String name, List<Long> value){
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.length = value.size();
        this.size = this.calculateSize();
        this.value = new ArrayList<>();
        this.value.addAll(value);
    }

    public TAGLongArray(String name, long[] value){
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.length = value.length;
        this.size = this.calculateSize();
        this.value = new ArrayList<>();
        for(long l : value) this.value.add(l);
    }

    public TAGLongArray(String name){
        this(name, new ArrayList<>());
    }

    private int calculateSize() {
        return length_size + data_size * this.length;
    }


    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('[');
        for (int i = 0; i < this.length; i++){
            if (i != 0 && i + 1 != this.length) result.append(",\u0020");
            result.append(this.value.get(i));
        }
        return result.append(']').toString();
    }

    @Override
    public String toString(boolean json) {
        return this.toString();
    }

    @Override
    public byte getTypeId() {
        return 12;
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
        int values_size = getValueSize() - length_size;
        byte[] length_bytes = ByteBuffer.allocate(length_size).putInt(this.length).array(),
                values_bytes = new byte[values_size],
                result = new byte[getValueSize()];
        for (int i = 0; i < this.length; i++) {
            byte[] value_bytes = ByteBuffer.allocate(data_size).putLong(this.value.get(i)).array();
            System.arraycopy(value_bytes, 0, values_bytes, data_size * i, data_size);
        }
        System.arraycopy(length_bytes, 0, result, 0 , length_size);
        System.arraycopy(values_bytes, 0, result, length_size, values_size);
        return result;
    }
}
