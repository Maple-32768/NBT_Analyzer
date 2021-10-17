import java.util.List;

public class TAGCompound extends TAGComponent{
    TAGHeader header;
    byte[] data_raw;
    List<TAGComponent> data;

    public TAGCompound(byte[] data){
        this.header = TAGHeader.getHeader(data);
        this.data_raw = data;
    }
}
