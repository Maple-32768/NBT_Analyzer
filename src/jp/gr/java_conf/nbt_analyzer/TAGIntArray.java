package jp.gr.java_conf.nbt_analyzer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAGIntArray extends TAGComponent {
    public static final byte TYPE_ID = 11;

    private static final int length_size = Integer.SIZE / Byte.SIZE;
    private static final int data_size = Integer.SIZE / Byte.SIZE;

    public TAGComponent parent;
    public TAGHeader header;
    public int length;
    public int[] value;
    public int size;

    public TAGIntArray(TAGHeader header, byte[] data) {
        this.parent = null;
        this.header = header;
        this.length = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, length_size)).getInt();
        this.size = this.calculateSize();
        this.value = new int[this.length];
        for (int i = 0; i < this.length; i++) {
            this.value[i] = ByteBuffer.wrap(Arrays.copyOfRange(data, length_size + i * data_size, length_size + (i + 1) * data_size)).getInt();
        }
    }

    public TAGIntArray(TAGComponent parent, TAGHeader header, byte[] data) throws IllegalArgumentException {
        this(header, data);
        this.setParent(parent);
    }

    public TAGIntArray(String name, List<Integer> value) {
        this.parent = null;
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.length = value.size();
        this.size = this.calculateSize();
        this.value = new int[this.length];
        for (int i = 0; i < this.length; i++) this.value[i] = value.get(i);
    }

    public TAGIntArray(String name, int[] value) {
        this.parent = null;
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.length = value.length;
        this.size = this.calculateSize();
        this.value = value.clone();
    }

    public TAGIntArray(String name) {
        this(name, new ArrayList<>());
    }

    private int calculateSize() {
        return length_size + data_size * this.length;
    }

    public void setValue(int[] value) {
        this.length = value.length;
        this.value = value.clone();
        this.size = this.calculateSize();
    }

    public void setValue(List<Integer> value) {
        int[] value_array = new int[value.size()];
        for (int i = 0; i < value.size(); i++) value_array[i] = value.get(i);
        this.setValue(value_array);
    }

    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('[');
        for (int i = 0; i < this.length; i++) {
            if (i != 0 && i + 1 != this.length) result.append(",\u0020");
            result.append(this.value[i]);
        }
        return result.append(']').toString();
    }

    @Override
    public String toString(boolean json) {
        return this.toString();
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
        int values_size = getValueSize() - length_size;
        byte[] length_bytes = ByteBuffer.allocate(length_size).putInt(this.length).array(),
                values_bytes = new byte[values_size],
                result = new byte[getValueSize()];
        for (int i = 0; i < this.length; i++) {
            byte[] value_bytes = ByteBuffer.allocate(data_size).putInt(this.value[i]).array();
            System.arraycopy(value_bytes, 0, values_bytes, data_size * i, data_size);
        }
        System.arraycopy(length_bytes, 0, result, 0, length_size);
        System.arraycopy(values_bytes, 0, result, length_size, values_size);
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param parent 親のNBTオブジェクト
     * @throws IllegalArgumentException
     */
    @Override
    public void setParent(TAGComponent parent) throws IllegalArgumentException {
        if (!TAGComponent.checkValidParent(parent)) throw new IllegalArgumentException("Invalid type of parent");
        this.parent = parent;
    }

    @Override
    public TAGComponent getParent() {
        return this.parent;
    }
}
