import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TAGEnd extends TAGComponent{

    public TAGHeader header;

    public TAGEnd(TAGHeader header){
        this.header = header;
    }

    public TAGEnd(){
        this.header = TAGHeader.getInstance(0, "");
    }

    @Override
    public TAGHeader getHeader() {
        return this.header;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String toString(boolean json) {
        return null;
    }

    @Override
    public int getSize() {
        return this.header.size;
    }

    @Override
    public int getValueSize() {
        return 0;
    }

    @Override
    public int getTypeId() {
        return 0;
    }
}
