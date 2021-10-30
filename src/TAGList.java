import java.nio.ByteBuffer;
import java.util.*;

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
        byte[] data_temp = Arrays.copyOfRange(data, type_size + length_size, data.length);
        for (int i = 0; i < this.length; i++) {
            TAGComponent c;
            c = getNoHeaderComponent(this.type, data_temp);
            value.add(c);
            data_temp = Arrays.copyOfRange(data_temp, Objects.requireNonNull(c).getSize(), data_temp.length);
        }
        this.size = this.calculateSize();
    }

    public TAGList(String name, byte type, List<TAGComponent> value){
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.type = type;
        this.length = value.size();
        this.value = new ArrayList<>();
        this.value.addAll(value);
        this.size = this.calculateSize();
    }

    public TAGList(String name, int type, List<TAGComponent> value){
        this(name, (byte)type, value);
    }

    public TAGList(String name, byte type, TAGComponent [] value){
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.type = type;
        this.length = value.length;
        this.value = new ArrayList<>();
        Collections.addAll(this.value, value);
        this.size = this.calculateSize();
    }

    public TAGList(String name){
        this(name, -1, new ArrayList<>());
    }

    public TAGList(String name, byte type){
        this(name, type, new ArrayList<>());
    }

    public TAGList(String name, int type){
        this(name, (byte)type);
    }

    public TAGList(String name, int type, TAGComponent [] value){
        this(name, (byte)type, value);
    }

    private int calculateSize() {
        int sum = type_size + length_size;
        for (TAGComponent c : this.value) sum += Objects.requireNonNull(c).getValueSize();
        return sum;
    }

    public void setValue(List<TAGComponent> c){
        byte type = this.type == -1 ? c.get(0).getTypeId() : this.type;
        for (TAGComponent tagComponent : c)
            if (tagComponent.getTypeId() != type) throw new IllegalArgumentException("Different type of elements");
        this.type = type;
        this.length = c.size();
        this.value = c;
        this.size = this.calculateSize();
    }

    public List<TAGComponent> getValue() {
        return this.value;
    }

    public void add(TAGComponent c){
        if (c.getTypeId() != this.type) throw new IllegalArgumentException("Different type of component");
        this.value.add(c);
        this.size = calculateSize();
    }

    public boolean remove(TAGComponent c){
        boolean result = this.value.remove(c);
        if (result) this.size = calculateSize();
        return result;
    }

    public boolean addAll(List<TAGComponent> c){
        byte type = this.type == -1 ? c.get(0).getTypeId() : this.type;
        for (TAGComponent tagComponent : c)
            if (tagComponent.getTypeId() != type) throw new IllegalArgumentException("Different type of elements");
        boolean result = this.value.addAll(c);
        if (result) this.size = calculateSize();
        return result;
    }

    public boolean addAll(int index, List<TAGComponent> c){
        byte type = this.type == -1 ? c.get(0).getTypeId() : this.type;
        for (TAGComponent tagComponent : c)
            if (tagComponent.getTypeId() != type) throw new IllegalArgumentException("Different type of elements");
        boolean result = this.value.addAll(index, c);
        if (result) this.size = this.calculateSize();
        return result;
    }

    public boolean removeAll(List<TAGComponent> c){
        boolean result = this.value.removeAll(c);
        if (result) this.size = this.calculateSize();
        return result;
    }

    public boolean retainAll(List<TAGComponent> c){
        boolean result = this.value.retainAll(c);
        if (result) this.size = this.calculateSize();
        return result;
    }

    public void clear(){
        this.value.clear();
        this.size = calculateSize();
    }

    public TAGComponent set(int index, TAGComponent element){
        if (element.getTypeId() != this.type) throw new IllegalArgumentException("Different type of component");
        TAGComponent result = this.value.set(index, element);
        this.size = this.calculateSize();
        return result;
    }

    public void add(int index, TAGComponent c){
        if (c.getTypeId() != this.type) throw new IllegalArgumentException("Different type of component");
        this.value.add(index, c);
        this.size = this.calculateSize();
    }

    public void remove(int index){
        this.value.remove(index);
        this.size = this.calculateSize();
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
