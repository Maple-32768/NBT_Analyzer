package jp.gr.java_conf.nbt_analyzer;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TAGLong extends TAGComponent {
    public static final byte TYPE_ID = 4;

    private static final int data_size = Long.SIZE / Byte.SIZE;

    public TAGComponent parent;
    public TAGHeader header;
    public long value;

    public TAGLong(TAGHeader header, byte[] data) {
        this.parent = null;
        this.header = header;
        value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).getLong();
    }

    public TAGLong(String name, long value) {
        this.parent = null;
        this.header = TAGHeader.getInstance(getTypeId(), name);
        this.value = value;
    }

    public TAGLong(String name) {
        this(name, 0L);
    }


    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
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
        return ByteBuffer.allocate(data_size).putLong(this.value).array();
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

    @Override
    public TAGLong clone() {
        TAGLong clone = (TAGLong) super.clone();
        clone.parent = null;
        clone.header = this.header.clone();
        return clone;
    }
}
