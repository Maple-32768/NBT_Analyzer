package jp.gr.java_conf.nbt_analyzer;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class TAGDouble extends TAGComponent {
    public static final byte TYPE_ID = 6;

    private static final int data_size = Double.SIZE / Byte.SIZE;

    public TAGComponent parent;
    public TAGHeader header;
    public double value;

    public TAGDouble(TAGHeader header, byte[] data) {
        this.parent = null;
        this.header = header;
        value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getDouble();
    }

    public TAGDouble(String name, double value) {
        this.parent = null;
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.value = value;
    }

    public TAGDouble(String name) {
        this(name, 0d);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
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
        return this.header.size + data_size;
    }

    @Override
    public int getValueSize() {
        return data_size;
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
        return ByteBuffer.allocate(data_size).putDouble(this.value).array();
    }

    /**
     * {@inheritDoc}
     *
     * @param parent ??????NBT??????????????????
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

    @Override
    public TAGDouble clone() {
        TAGDouble clone = (TAGDouble) super.clone();
        clone.parent = null;
        clone.header = this.header.clone();
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TAGDouble)) return false;
        TAGDouble tagDouble = (TAGDouble) o;
        return Double.compare(tagDouble.value, value) == 0 && header.equals(tagDouble.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, value);
    }
}
