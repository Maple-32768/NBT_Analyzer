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
        this.size = data_size * this.length;
        value = new ArrayList<>();
        for (int i = 0; i < this.length; i++){
            value.add(ByteBuffer.wrap(Arrays.copyOfRange(data, length_size + i * data_size, length_size + (i + 1) * data_size)).getLong());
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
    public int getTypeId() {
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
}
