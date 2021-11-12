import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * NBTのByteタグを扱う具象クラスです。
 * @author Maple32768
 * @version 1.1
 */
public class TAGByte extends TAGComponent{
	/**
	 * Byteタグ固有のid
	 */
	public static final byte TYPE_ID = 1;

	private static final int data_size = 1;

	/**
	 * タグのヘッダーオブジェクト
	 */
	public TAGHeader header;
	/**
	 * タグのデータ
	 */
	public byte value;

	/**
	 * ヘッダーオブジェクトとデータ部分から始まるバイト配列からデータを取り出し、Byteタグのインスタンスを生成します。
	 * @param header タグのヘッダーオブジェクト
	 * @param data タグのデータ部分から始まるバイト配列
	 */
	public TAGByte(TAGHeader header, byte[] data) {
		this.header = header;
		this.value = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, data_size)).get();
	}

	/**
	 * タグの名前を指定してデフォルト値({@code 0})を持ったByteタグのインスタンスを生成します。
	 * @param name タグの名前
	 */
	public TAGByte(String name){
		this(name, (byte)0);
	}

	/**
	 * タグの名前と値を指定してByteタグのインスタンスを生成します。
	 * @param name タグの名前
	 * @param value 値
	 */
	public TAGByte(String name, byte value){
		this.header = TAGHeader.getInstance(getTypeId(), name);
		this.value = value;
	}

	/**
	 * タグの値を指定したものに変更します。
	 * @param value 新しい値
	 */
	public void setValue(byte value){
		this.value = value;
	}

	/**
	 * タグが持っている値を返します。
	 * @return タグが持っている値
	 */
	public byte getValue() {
		return this.value;
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
	 * これは8bit符号付き整数({@code -256}以上{@code 256}未満)です。
	 * @return タグの文字列表現
	 */
	@Override
	public String toString() {
		return new String(new byte[] {this.value});
	}

	@Override
	public String toString(boolean json) {
		return this.toString();
	}

	/**
	 * {@inheritDoc}
	 * これは常に{@code 1}です。
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
		return this.header.size + data_size;
	}

	/**
	 * {@inheritDoc}
	 * @return タグの値のみのサイズ
	 */
	@Override
	public int getValueSize() {
		return data_size;
	}

	/**
	 * {@inheritDoc}
	 * @return ヘッダーを含めたタグのバイト配列
	 */
	@Override
	public byte[] getBytes() {
		byte[] header_bytes = this.header.getBytes(),
				value_bytes = this.getValueBytes(),
				result = new byte[getSize()];
		System.arraycopy(header_bytes, 0, result, 0, header_bytes.length);
		System.arraycopy(value_bytes, 0, result, header_bytes.length, value_bytes.length);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @return タグの値のみのバイト配列
	 */
	@Override
	public byte[] getValueBytes() {
		return ByteBuffer.allocate(data_size).put(this.value).array();
	}
}
