package jp.gr.java_conf.nbt_analyzer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class TAGHeader implements Cloneable {
    private static final int type_length = 1;
    private static final int name_size_length = Short.SIZE / Byte.SIZE;

    public byte type;
    public short name_size;
    public String tag_name;
    public int size;

    public TAGHeader(byte[] data) {
        this.type = data[0];
        if (this.type == 0x00) {
            this.size = type_length;
            return;
        }
        this.name_size = ByteBuffer.wrap(Arrays.copyOfRange(data, type_length, type_length + name_size_length)).getShort();
        if (this.name_size == 0) this.tag_name = null;
        else
            this.tag_name = new String(Arrays.copyOfRange(data, type_length + name_size_length, type_length + name_size_length + this.name_size));
        this.size = this.calculateSize();
    }

    public TAGHeader(byte type, String name) {
        this.type = type;
        if (this.type == 0x00) {
            this.size = type_length;
            return;
        }
        this.tag_name = name;
        this.name_size = (short) name.getBytes(StandardCharsets.UTF_8).length;
        this.size = this.calculateSize();
    }

    public TAGHeader(int type, String name) {
        this((byte) type, name);
    }

    private int calculateSize() {
        return type_length + name_size_length + this.name_size;
    }

    public String getTagName(boolean json) {
        String result = this.tag_name;
        if (json) result = '\"' + result + '\"';
        return result;
    }

    public static TAGHeader getInstance(byte type, String name) {
        return new TAGHeader(type, name);
    }

    public static TAGHeader getInstance(int type, String name) {
        return getInstance((byte) type, name);
    }

    public static TAGHeader getHeader(byte[] raw_data) {
        return new TAGHeader(raw_data);
    }

    public static TAGHeader getNullHeader(byte type) {
        TAGHeader result = new TAGHeader(new byte[]{(byte) 0x00});
        result.type = type;
        return result;
    }

    public static TAGHeader getNullHeader(int type) {
        return getNullHeader((byte) type);
    }

    public String toString() {
        return this.tag_name == null || this.tag_name.equals("") ? "" : this.tag_name + ":";
    }

    public byte[] getBytes() {
        if (this.type == 0) return new byte[]{0x00};
        byte[] result = new byte[type_length + name_size_length + this.name_size];
        result[0] = this.type;
        byte[] name_length_bytes = ByteBuffer.allocate(name_size_length).putShort(this.name_size).array();
        int prefix_length = type_length + name_size_length;
        System.arraycopy(name_length_bytes, 0, result, type_length, name_size_length);
        if (this.name_size != 0) {
            byte[] name_bytes = this.tag_name.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(name_bytes, 0, result, prefix_length, name_size);
        }
        return result;
    }

    @Override
    public TAGHeader clone() {
        TAGHeader clone = null;
        try {
            clone = (TAGHeader) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TAGHeader tagHeader = (TAGHeader) o;
        return type == tagHeader.type && name_size == tagHeader.name_size && size == tagHeader.size && tag_name.equals(tagHeader.tag_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name_size, tag_name, size);
    }
}
