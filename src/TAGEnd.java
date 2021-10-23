import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TAGEnd extends TAGComponent{
    public static final int data_size = 0;
    public static final int size = 0;

    public TAGHeader header;

    public TAGEnd(TAGHeader header){
        this.header = header;
    }

    public TAGEnd(){
        this.header = TAGHeader.getInstance(0, "");
    }

    @Contract(" -> new")
    public static @NotNull TAGEnd getInstance(){
        return new TAGEnd();
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
        return size;
    }
}
