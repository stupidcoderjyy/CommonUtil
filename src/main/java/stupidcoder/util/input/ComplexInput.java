package stupidcoder.util.input;

public abstract class ComplexInput implements IInput, AutoCloseable{
    int row = 0;
    int column = 0;
    int begin = 0;

    public abstract String currentLine();

    final void newLine() {
        row++;
        column = 0;
    }

    final void move() {
        column++;
    }

    public final void skipLine() {
        find('\n');
    }

    public final int posRow() {
        return row;
    }

    public final int posColumn() {
        return column;
    }

    public final int posColumnMark() {
        return begin;
    }

    public final void markColumn() {
        this.begin = column;
    }
}
