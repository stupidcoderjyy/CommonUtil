package stupidcoder.util.input;

import java.io.PrintStream;

public class CompileException extends Exception{
    private final int line;
    private int start, end;
    private final String content;
    private final String filePath;

    public CompileException(int line, String content, String filePath) {
        this.line = line;
        this.content = content;
        this.filePath = filePath;
    }

    public CompileException(String message, int line, String content, String filePath) {
        super(message);
        this.line = line;
        this.content = content;
        this.filePath = filePath;
    }

    public CompileException setPos(int column) {
        start = column;
        end = column;
        return this;
    }

    public CompileException setPos(int start, int end) {
        this.start = start;
        this.end = end;
        return this;
    }

    @Override
    public void printStackTrace(PrintStream s) {
        s.print(filePath);
        s.print(" [" + line + ":" + start + "]:" + getMessage());
        s.println();
        s.println("    " + content);
        s.println(" ".repeat(start + 4) + "^".repeat(end - start + 1));
    }
}
