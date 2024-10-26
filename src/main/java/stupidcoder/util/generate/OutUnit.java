package stupidcoder.util.generate;

import stupidcoder.util.input.BufferedInput;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class OutUnit {
    protected Source src = Source.EMPTY;
    public final Map<String, OutUnitField> fields = new HashMap<>();
    protected int lineBreaks = -1;
    protected int indents = -1;
    protected int repeat = -1;
    protected boolean nativeSrc;
    protected BufferedInput input;

    public abstract void writeContentOnce(FileWriter writer, BufferedInput srcIn) throws Exception;

    public void setSrc(Source src) {
        this.src = src;
        this.nativeSrc = src != Source.EMPTY;
    }

    public Source getSrc() {
        return src;
    }

    public void writeAll(FileWriter writer, BufferedInput parentSrc) throws Exception{
        try {
            initConfig();
            if (nativeSrc && src.used) {
                src.recall();
            }
            if (input == null) {
                input = nativeSrc ? new BufferedInput(src) : parentSrc;
            }
            writeContents(writer);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("OutUnit:{" + this + "}, Source: " + (src == Source.EMPTY ? "NULL" : src));
            throw new RuntimeException();
        }
    }

    protected void writeContents(FileWriter writer) throws Exception{
        for (int i = 0; shouldRepeat(i, input) ; i++) {
            writer.write("    ".repeat(indents));
            writeContentOnce(writer, input);
            writer.write("\r\n".repeat(lineBreaks));
        }
    }

    public void close() {
        if (input != null) {
            input.close();
        }
    }

    protected boolean shouldRepeat(int count, BufferedInput input) {
        if (count >= repeat) {
            return false;
        }
        return input == null || input.available();
    }

    protected final int readInt(BufferedInput input) {
        return (input.read() << 24) | (input.read() << 16) | (input.read() << 8) | (input.read() & 0xFF);
    }

    protected final String readString(BufferedInput input) {
        int count = readInt(input);
        input.mark();
        input.read(count);
        input.mark();
        return input.capture();
    }

    protected void initConfig() {
        lineBreaks = Math.max(0, lineBreaks);
        indents = Math.max(0, indents);
        if (repeat < 0) {
            repeat = 1;
        }
    }

    public void setLineBreaks(int lineBreaks) {
        this.lineBreaks = lineBreaks;
    }

    public void setIndents(int indents) {
        this.indents = indents;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int lineBreaks() {
        return lineBreaks;
    }

    public int indents() {
        return indents;
    }

    public int repeat() {
        return repeat;
    }
}
