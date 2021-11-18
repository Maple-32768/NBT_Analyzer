package jp.gr.java_conf.nbt_analyzer;

import java.util.*;

public class TAGCompound extends TAGComponent {
    public static final byte TYPE_ID = 10;

    public TAGComponent parent;
    public TAGHeader header;
    public HashMap<String, TAGComponent> value;
    public int size;

    public TAGCompound(TAGHeader header, byte[] data) {
        this.parent = null;
        this.header = header;
        this.value = new HashMap<>();
        byte[] data_temp = data.clone();
        while (true) {
            TAGComponent c = TAGComponent.Analyze(this, data_temp);
            if (c instanceof TAGEnd) break;
            this.value.put(Objects.requireNonNull(c).getHeader().tag_name, c);
            if (c.getSize() >= data_temp.length) throw new IllegalArgumentException("Invalid NBT format.");
            data_temp = Arrays.copyOfRange(data_temp, c.getSize(), data_temp.length);
        }
        this.size = this.calculateSize();
    }

    public TAGCompound(TAGComponent parent, TAGHeader header, byte[] data) throws IllegalArgumentException {
        this(header, data);
        this.setParent(parent);
    }

    public TAGCompound(String name, List<TAGComponent> value) {
        this.parent = null;
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.value = new HashMap<>();
        for (TAGComponent c : value) {
            this.value.put(c.getHeader().tag_name, c);
            c.setParent(this);
        }
        this.size = this.calculateSize();
    }

    public TAGCompound(TAGComponent parent, String name, List<TAGComponent> value) throws IllegalArgumentException {
        this(name, value);
        this.setParent(parent);
    }

    public TAGCompound(String name, TAGComponent[] value) {
        this(name, Arrays.asList(value));
    }

    public TAGCompound(TAGComponent parent, String name, TAGComponent[] value) throws IllegalArgumentException {
        this(name, Arrays.asList(value));
        this.setParent(parent);
    }

    public TAGCompound(String name) {
        this(name, new ArrayList<>());
    }

    public TAGCompound(TAGComponent parent, String name) throws IllegalArgumentException {
        this(name, new ArrayList<>());
        this.setParent(parent);
    }

    public TAGCompound() {
        this("");
    }

    public TAGCompound(TAGComponent parent) throws IllegalArgumentException {
        this("");
        this.setParent(parent);
    }

    private int calculateSize() {
        int sum = 1;
        for (TAGComponent c : this.value.values()) sum += c.getSize();
        return sum;
    }

    public void setValue(List<TAGComponent> value) {
        this.value = new HashMap<>();
        for (TAGComponent c : value) {
            this.value.put(c.getHeader().tag_name, c);
            c.setParent(this);
        }
        this.size = calculateSize();
    }

    public void setValue(TAGComponent[] value) {
        this.setValue(Arrays.asList(value));
    }

    public HashMap<String, TAGComponent> getValue() {
        return this.value;
    }

    public void put(TAGComponent c) {
        this.value.put(c.getHeader().tag_name, c);
        c.setParent(this);
        this.size = calculateSize();
    }

    public List<TAGComponent> putAll(List<TAGComponent> elements) {
        List<TAGComponent> result = new ArrayList<>();
        for (TAGComponent c : elements) {
            TAGComponent res = this.value.put(c.getHeader().tag_name, c);
            c.setParent(this);
            if (res != null) result.add(res);
        }
        this.size = calculateSize();
        return result;
    }

    public List<TAGComponent> putAll(TAGComponent[] elements) {
        return this.putAll(Arrays.asList(elements));
    }

    public boolean remove(TAGComponent c) {
        boolean result = this.value.remove(c.getHeader().tag_name) != null;
        if (result) {
            c.setParent(null);
            this.size = calculateSize();
        }
        return result;
    }

    public boolean removeAll(List<TAGComponent> elements) {
        boolean result = false;
        for (TAGComponent c : elements) {
            boolean result1 =this.value.remove(c.getHeader().tag_name, c);
            if (result1) c.setParent(null);
            result = result || result1;
        }
        this.size = this.calculateSize();
        return result;
    }

    public boolean removeAll(TAGComponent[] elements) {
        return this.removeAll(Arrays.asList(elements));
    }

    public boolean removeAllFromKeys(List<String> keys) {
        boolean result = false;
        for (String key : keys) {
            TAGComponent c = this.value.remove(key);
            boolean result1 = c != null;
            if (result1) c.setParent(null);
            result = result || result1;
        }
        this.size = this.calculateSize();
        return result;
    }

    public boolean removeAllFromKeys(String[] keys) {
        return this.removeAllFromKeys(Arrays.asList(keys));
    }

    public void clear() {
        for (TAGComponent c : this.value.values()) c.setParent(null);
        this.value.clear();
        this.size = calculateSize();
    }

    public TAGComponent putIfAbsent(TAGComponent c) {
        TAGComponent result = this.value.putIfAbsent(c.getHeader().tag_name, c);
        if (result == null) c.setParent(this);
        return result;
    }

    public boolean replace(TAGComponent oldValue, TAGComponent newValue) {
        boolean result = this.value.replace(oldValue.getHeader().tag_name, oldValue, newValue);
        if (result){
            oldValue.setParent(null);
            newValue.setParent(this);
        }
        this.size = this.calculateSize();
        return result;
    }

    @Override
    public TAGHeader getHeader() {
        return header;
    }

    @Override
    public String toString(boolean json) {
        StringBuilder result = new StringBuilder();
        result.append('{');
        for (int i = 0; i < this.value.size(); i++) {
            TAGComponent c = (TAGComponent) this.value.values().toArray()[i];
            if (i != 0) result.append(",\u0020");
            result.append(c.getHeader()).append(c.toString(json));
        }
        result.append(new TAGEnd());
        return result.append('}').toString();
    }

    @Override
    public String toString() {
        return this.toString(false);
    }

    @Override
    public byte getTypeId() {
        return TYPE_ID;
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
        for (TAGComponent c : this.value.values()) {
            byte[] value_bytes = c.getBytes();
            System.arraycopy(value_bytes, 0, values_bytes, sum, value_bytes.length);
            sum += c.getSize();
        }
        return values_bytes;
    }

    /**
     * {@inheritDoc}
     *
     * @param parent 親のNBTオブジェクト
     * @throws IllegalArgumentException
     */
    @Override
    public void setParent(TAGComponent parent) {
        if (!TAGComponent.checkValidParent(parent)) throw new IllegalArgumentException("Invalid type of parent");
        this.parent = parent;
    }

    @Override
    public TAGComponent getParent() {
        return this.parent;
    }
}
