import org.jetbrains.annotations.NotNull;

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

    public TAGList(String name, byte type, @NotNull List<TAGComponent> value){
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

    public TAGList(String name, byte type, TAGComponent @NotNull [] value){
        new TAGList(name, type, Arrays.asList(value));
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
            result.append(c.getHeader().tag_name != null ? c.getHeader().getTagName(json) + " : " : "").append(c.toString(json));
        }
        return result.append(']').toString();
    }

    @Override
    public String toString(){
        return this.toString(false);
    }

    @Override
    public int getTypeId() {
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
}
