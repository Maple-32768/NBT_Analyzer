package jp.gr.java_conf.nbt_analyzer;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class TAGShort extends TAGComponent {
    public static final byte TYPE_ID = 2;

    private static final int data_size = Short.SIZE / Byte.SIZE;

    public TAGComponent parent;
    public TAGHeader header;
    public short value;

    public TAGShort(TAGHeader header, byte[] data) {
        this.parent = null;
        this.header = header;
        value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getShort();
    }

    public TAGShort(String name, short value) {
        this.parent = null;
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.value = value;
    }

    public TAGShort(String name) {
        this(name, (short) 0);
    }

    public void setValue(short value) {
        this.value = value;
    }

    public short getValue() {
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
        return ByteBuffer.allocate(data_size).putShort(this.value).array();
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
        return null;
    }

    @Override
    public TAGShort clone() {
        TAGShort clone = (TAGShort) super.clone();
        clone.parent = null;
        clone.header = this.header.clone();
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TAGShort)) return false;
        TAGShort tagShort = (TAGShort) o;
        return value == tagShort.value && header.equals(tagShort.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, value);
    }
}
