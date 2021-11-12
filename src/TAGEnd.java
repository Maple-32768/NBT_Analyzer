/**
 * NBTのEndタグを扱う具象クラスです。
 * @author Maple32768
 * @version 1.1
 */
public class TAGEnd extends TAGComponent{
    /**
     * Endタグ固有のタグid
     */
    public static final byte TYPE_ID = 0;

    /**
     * タグのヘッダーオブジェクト
     */
    public TAGHeader header;

    /**
     * Endタグのインスタンスを生成します。
     */
    public TAGEnd(){
        this.header = TAGHeader.getInstance(0, "");
    }

    /**
     * ヘッダーからEndタグのインスタンスを生成します。
     * @param header タグのヘッダーオブジェクト
     */
    public TAGEnd(TAGHeader header){
        this.header = header;
    }

    /**
     * {@inheritDoc}
     * @return タグのヘッダーオブジェクト
     */
    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    /**
     * {@inheritDoc}
     * これは常に空文字列です。
     * @return タグの文字列表現
     */
    @Override
    public String toString() {
        return "";
    }

    @Override
    public String toString(boolean json) {
        return "";
    }

    /**
     * {@inheritDoc}
     * これは常に{@code 0}です。
     * @return タグの種類のid
     */
    @Override
    public byte getTypeId() {
        return TYPE_ID;
    }

    /**
     * {@inheritDoc}
     * @return ヘッダーを含めたタグのサイズ
     */
    @Override
    public int getSize() {
        return this.header.size;
    }

    /**
     * {@inheritDoc}
     * @return タグの値のみのサイズ
     */
    @Override
    public int getValueSize() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * @return ヘッダーを含めたタグのバイト配列
     */
    @Override
    public byte[] getBytes() {
        return this.header.getBytes();
    }

    /**
     * {@inheritDoc}
     * @return タグの値のみのバイト配列
     */
    @Override
    public byte[] getValueBytes() {
        return new byte[0];
    }
}
