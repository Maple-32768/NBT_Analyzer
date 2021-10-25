import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGCompound extends TAGComponent{
    public TAGHeader header;
    public List<TAGComponent> value;
    public int size;

    public TAGCompound(TAGHeader header, byte [] data){
        this.header = header;
        this.value = new ArrayList<>();
        byte[] data_temp = data.clone();
        while(true){
            TAGComponent c = TAGComponent.Analyze(data_temp);
            this.value.add(c);
            if (c instanceof TAGEnd) break;
            if (c.getSize() >= data_temp.length) throw new IllegalArgumentException("Invalid NBT format.");
            data_temp = Arrays.copyOfRange(data_temp, c.getSize(), data_temp.length);
        }
        this.size = this.calculateSize();
    }

    public TAGCompound(String name, List<TAGComponent> value){
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.value = new ArrayList<>();
        this.value.addAll(value);
        this.size = this.calculateSize();
    }

    public TAGCompound(String name, TAGComponent[] value){
        this(name, Arrays.asList(value));
    }

    public TAGCompound(String name){
        this(name, new ArrayList<>());
    }

    private int calculateSize() {
        int sum = 0;
        for (TAGComponent c : this.value) sum += c.getSize();
        return sum;
    }

    @Override
    public TAGHeader getHeader() {
        return header;
    }


    public String toString(boolean json) {
        StringBuilder result = new StringBuilder();
        result.append('{');
        for(int i = 0; i < this.value.size(); i++){
            TAGComponent c = this.value.get(i);
            if (i != 0) result.append(",\u0020");
            result.append(c.getHeader()).append(c.toString(json));
        }
        return result.append('}').toString();
    }

    @Override
    public String toString(){
        return this.toString(false);
    }

    @Override
    public byte getTypeId() {
        return 10;
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
        byte[] values_bytes = new byte[this.getValueSize()];
        int sum = 0;
        for (TAGComponent c : this.value) {
            byte[] value_bytes = c.getBytes();
            System.arraycopy(value_bytes, 0, values_bytes, sum, value_bytes.length);
            sum += c.getSize();
        }
        return values_bytes;
    }
}
