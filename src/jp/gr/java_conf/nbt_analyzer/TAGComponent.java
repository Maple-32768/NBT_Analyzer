package jp.gr.java_conf.nbt_analyzer;

import java.util.Arrays;

/**
 * NBTオブジェクトを実装する為の抽象クラスです。
 *
 * @author Maple32768
 * @version 1.1
 */
public abstract class TAGComponent {

    private static final byte[] valid_parents_id = {9, 10};

    /**
     * タグが持っているヘッダーオブジェクトを返します。
     *
     * @return タグのヘッダーオブジェクト
     */
    abstract public TAGHeader getHeader();

    /**
     * タグの文字列表現を返します。
     * {@inheritDoc}
     *
     * @return タグの文字列表現
     */
    abstract public String toString();

    abstract public String toString(boolean json);

    /**
     * タグの種類によって固有のidを返します。
     *
     * @return タグの種類のid
     */
    abstract public byte getTypeId();

    /**
     * ヘッダー部分を含めたタグ全体のバイト列のサイズを返します。
     *
     * @return ヘッダーを含めたタグのサイズ
     */
    abstract public int getSize();

    /**
     * ヘッダー部分を除いたタグの値のみのバイト列のサイズを返します。
     *
     * @return タグの値のみのサイズ
     */
    abstract public int getValueSize();

    /**
     * ヘッダー部分を含めたタグ全体のバイト配列を返します。
     *
     * @return ヘッダーを含めたタグのバイト配列
     */
    abstract public byte[] getBytes();

    /**
     * ヘッダー部分を除いたタグの値のみのバイト配列を返します。
     *
     * @return タグの値のみのバイト配列
     */
    abstract public byte[] getValueBytes();

    /**
     * タグの親オブジェクトを変更します。
     *
     * @param parent 親のNBTオブジェクト
     * @throws IllegalArgumentException 親オブジェクトが子オブジェクトを持つことができない場合
     */
    abstract public void setParent(TAGComponent parent) throws IllegalArgumentException;

    /**
     * タグが属する親のNBTオブジェクトを返します。
     * この関数は次の場合に{@code null}を返す場合があります。<br>
     * 1.目的のオブジェクトがCompound型のルートオブジェクトだった場合<br>
     * 2.目的のオブジェクトが意図的に作られた親を持たないオブジェクトであった場合
     *
     * @return 親のNBTオブジェクトまたはnull
     */
    abstract public TAGComponent getParent();

    /**
     * バイト配列からNBTオブジェクトを生成し返します。
     * バイト配列の形式からタグの種類を判別出来ない場合(先頭1バイトが0以上12以下ではなかった場合){@code null}を返します。
     *
     * @param data 解析対象のバイト配列
     * @return 生成されたNBTオブジェクト
     */
    public static TAGComponent Analyze(byte[] data) {
        TAGComponent result = null;
        TAGHeader header = TAGHeader.getHeader(data);
        if (header.type == 0) return new TAGEnd(header);
        byte[] data_temp = Arrays.copyOfRange(data, header.size, data.length);
        switch (header.type) {
            case 1:
                result = new TAGByte(header, data_temp);
                break;

            case 2:
                result = new TAGShort(header, data_temp);
                break;

            case 3:
                result = new TAGInt(header, data_temp);
                break;

            case 4:
                result = new TAGLong(header, data_temp);
                break;

            case 5:
                result = new TAGFloat(header, data_temp);
                break;

            case 6:
                result = new TAGDouble(header, data_temp);
                break;

            case 7:
                result = new TAGByteArray(header, data_temp);
                break;

            case 8:
                result = new TAGString(header, data_temp);
                break;

            case 9:
                result = new TAGList(header, data_temp);
                break;

            case 10:
                result = new TAGCompound(header, data_temp);
                break;

            case 11:
                result = new TAGIntArray(header, data_temp);
                break;

            case 12:
                result = new TAGLongArray(header, data_temp);
                break;

        }
        return result;
    }


    /**
     * タグの種類とバイト配列から名前がないヘッダーを持つListタグの要素用のNBTオブジェクトを生成し返します。
     * バイト配列の形式からタグの種類を判別出来ない場合(typeが0以上12以下ではなかった場合){@code null}を返します。
     *
     * @param type 目的のNBTタグの種類
     * @param data 解析対象のバイト配列
     * @return 解析されたNBTオブジェクト
     */
    public static TAGComponent getNoHeaderComponent(byte type, byte[] data) {
        TAGHeader null_header = TAGHeader.getNullHeader(type);
        switch (type) {
            case 0:
                return new TAGEnd(null_header);

            case 1:
                return new TAGByte(null_header, data);

            case 2:
                return new TAGShort(null_header, data);

            case 3:
                return new TAGInt(null_header, data);

            case 4:
                return new TAGLong(null_header, data);

            case 5:
                return new TAGFloat(null_header, data);

            case 6:
                return new TAGDouble(null_header, data);

            case 7:
                return new TAGByteArray(null_header, data);

            case 8:
                return new TAGString(null_header, data);

            case 9:
                return new TAGList(null_header, data);

            case 10:
                return new TAGCompound(null_header, data);

            case 11:
                return new TAGIntArray(null_header, data);

            case 12:
                return new TAGLongArray(null_header, data);

        }
        return null;
    }

    /**
     * NBTオブジェクトが他のNBTオブジェクトの親として渡すことが可能な型かどうかを返します。
     *
     * @param parent 判定する親のNBTオブジェクト
     * @return 親として渡すことが出来る型の場合はtrue, それ以外はfalse
     */
    public static boolean checkValidParent(TAGComponent parent) {
        for (byte type : TAGComponent.valid_parents_id) {
            if (parent.getTypeId() == type) return true;
        }
        return false;
    }
}
