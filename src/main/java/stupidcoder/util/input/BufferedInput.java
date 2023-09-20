package stupidcoder.util.input;

import stupidcoder.util.input.readers.ConsoleByteReader;
import stupidcoder.util.input.readers.FileByteReader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BufferedInput extends ComplexInput {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private static final int MAX_BUFFER_SIZE = 4096;
    private final int bufEndA, bufEndB;
    private final int maxLexemeLen;
    private int inputEnd = -1;
    private final IByteReader reader;
    private byte[] buffer;
    private int forward;
    private int lexemeStart;
    private int fillCount;
    private boolean ignoreLexemeLimit = false;

    public BufferedInput(IByteReader reader, int bufSize) {
        if (bufSize <= 0 || bufSize > MAX_BUFFER_SIZE) {
            throw new IllegalArgumentException("invalid size:" + bufSize + ", required:(0, 4096]");
        }
        if (reader == null) {
            throw new NullPointerException("null reader");
        }
        this.buffer = new byte[bufSize * 2];
        this.forward = 0;
        this.lexemeStart = 0;
        this.reader = reader;
        this.bufEndA = bufSize;
        this.bufEndB = bufSize * 2;
        this.maxLexemeLen = bufSize;
        fill(0);
    }

    public BufferedInput(IByteReader reader) {
        this(reader, DEFAULT_BUFFER_SIZE);
    }

    public static BufferedInput fromFile(String filePath, int bufSize) {
        try {
            return new BufferedInput(new FileByteReader(filePath), bufSize);
        } catch (FileNotFoundException e) {
            throw new InputException(null, e);
        }
    }

    public static BufferedInput fromFile(String filePath) {
        return fromFile(filePath, DEFAULT_BUFFER_SIZE);
    }

    public static BufferedInput fromResource(String srcPath, int bufSize) {
        try {
            InputStream stream = BufferedInput.class.getResourceAsStream(srcPath);
            if (stream == null) {
                throw new FileNotFoundException(srcPath);
            }
            return new BufferedInput(new FileByteReader(stream), bufSize);
        } catch (FileNotFoundException e) {
            throw new InputException(null, e);
        }
    }

    public static BufferedInput fromResource(String srcPath) {
        return fromResource(srcPath, DEFAULT_BUFFER_SIZE);
    }

    public static BufferedInput fromConsole(int bufSize) {
        return new BufferedInput(new ConsoleByteReader(), bufSize);
    }

    public static BufferedInput fromConsole() {
        return new BufferedInput(new ConsoleByteReader());
    }

    public void ignoreLexemeLengthLimit() {
        this.ignoreLexemeLimit = true;
    }

    @Override
    public boolean isOpen() {
        return buffer != null;
    }

    @Override
    public boolean available() {
        if (!hasNext()) {
            return false;
        }
        if (ignoreLexemeLimit) {
            return true;
        }
        if (lexemeStart <= forward) {
            return forward - lexemeStart < maxLexemeLen;
        }
        return forward + bufEndB - lexemeStart < maxLexemeLen;
    }

    @Override
    public int read() {
        checkAvailable();
        byte result = buffer[forward];
        forward++;
        if (forward == bufEndB) {
            forward = 0;
            if (fillCount % 2 == 0) {
                fill(0);
            }
        } else if (forward == bufEndA) {
            if (fillCount % 2 == 1) {
                fill(bufEndA);
            }
        }
        switch (result) {
            case '\n' -> newLine();
            case '\r' -> {}
            default -> move();
        }
        return result;
    }

    @Override
    public void skip(int count) {
        for (int i = 0 ; i < count ; i ++) {
            read();
        }
    }

    private void fill(int begin) {
        fillCount++;
        int size = reader.read(buffer, begin, bufEndA);
        if (size < bufEndA) {
            inputEnd = begin + size;
        }
    }

    @Override
    public boolean hasNext() {
        if (inputEnd > 0) {
            return forward != inputEnd;
        }
        return true;
    }

    @Override
    public String lexeme() {
        checkOpen();
        final int start = lexemeStart;
        lexemeStart = forward;
        return lexeme(start, forward);
    }

    @Override
    public byte[] bytesLexeme() {
        final int start = lexemeStart;
        lexemeStart = forward;
        byte[] res;
        if (start < forward) {
            res = new byte[forward - start];
            System.arraycopy(buffer, start, res, 0, res.length);
        } else if (start > forward){
            int lenB = bufEndB - start;
            int lenA = forward;
            res = new byte[lenB + lenA];
            System.arraycopy(buffer, start, res, 0, lenB);
            System.arraycopy(buffer, 0, res, lenB, lenA);
        } else {
            res = new byte[0];
        }
        return res;
    }

    @Override
    public void markLexemeStart() {
        lexemeStart = forward;
    }

    @Override
    public int retract() {
        checkOpen();
        if (forward == 0) {
            if (fillCount == 1 || (fillCount & 1) == 0) {
                throw new InputException("exceed retract limit");
            }
            forward = bufEndB - 1;
            if (lexemeStart == 0) {
                lexemeStart = forward;
            }
        } else if (forward == bufEndA) {
            if ((fillCount & 1) == 1) {
                throw new InputException("exceed retract limit");
            }
            forward--;
            lexemeStart = Math.min(lexemeStart, forward);
        } else {
            if (lexemeStart == forward) {
                lexemeStart--;
            }
            forward--;
        }
        int b = buffer[forward];
        switch (b) {
            case '\r' -> {}
            case '\n' -> row--;
            default -> column--;
        }
        return b;
    }

    @Override
    public void close() {
        buffer = null;
        reader.close();
    }

    @Override
    public String currentLine() {
        int start = forward - column;
        int end = forward;
        if (start < 0) {
            start = Math.max(bufEndA, bufEndB + start);
        }
        if (buffer[forward] == '\n') {
            start--;
            end--;
            if (end < 0) {
                end = bufEndB - 1;
            }
        }
        return lexeme(start, end);
    }

    private String lexeme(int start, int end) {
        if (start < end) {
            return new String(buffer, start, end - start, StandardCharsets.UTF_8);
        } else if (start > end){
            int lenB = bufEndB - start;
            byte[] temp = new byte[lenB + end];
            System.arraycopy(buffer, start, temp, 0, lenB);
            System.arraycopy(buffer, 0, temp, lenB, end);
            return new String(temp, StandardCharsets.UTF_8);
        }
        return "";
    }
}
