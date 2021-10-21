import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGCompound extends TAGComponent{
    public TAGHeader header;
    public byte[] data_raw;
    public List<TAGComponent> value;
    public int size;

    public TAGCompound(TAGHeader header, byte[] data){
        this.header = header;
        this.data_raw = data;
        this.value = new ArrayList<>();
        this.size = 0;
        byte[] data_temp = data_raw.clone();
        while(true){
            TAGComponent c = TAGComponent.Analyze(data_temp);
            if (c instanceof TAGEnd) {
                this.size++;
                break;
            }
            else {
                this.value.add(c);
                this.size += c.getHeader().size + c.getSize();
            }
            if (c.getHeader().size + c.getSize() >= data_temp.length) throw new IllegalArgumentException("Invalid NBT format.");
            data_temp = Arrays.copyOfRange(data_temp, c.getHeader().size + c.getSize(), data_temp.length);
        }

    }

    @Override
    public TAGHeader getHeader() {
        return header;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('{');
        for(int i = 0; i < this.value.size(); i++){
            TAGComponent c = this.value.get(i);
            if (i != 0) result.append(",\u0020");
            result.append(c.getHeader().tag_name != null ? c.getHeader().tag_name + " : " : "").append(c);
        }
        return result.append('}').toString();
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
