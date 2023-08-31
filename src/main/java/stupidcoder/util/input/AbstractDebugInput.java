package stupidcoder.util.input;

import stupidcoder.util.ASCII;

import java.io.PrintStream;

public abstract class AbstractDebugInput implements IInput{
    protected abstract String type();
    protected abstract void printData();
    protected abstract void printPos();

    protected static void printBuffer(byte[] buffer, int start, int limit) {
        PrintStream s = System.err;
        if (buffer == null) {
            s.println("null buffer");
            return;
        }
        int pos = start;
        while (pos < limit) {
            printSingleLine(buffer, pos, limit);
            pos += 20;
        }
    }

    protected static void printSingleLine(byte[] buffer, int start, int limit) {
        PrintStream s = System.err;
        s.print("    ");
        limit = Math.min(start + 19, limit - 1);
        String prefix = start + "-" + limit + ":";
        if (prefix.length() < 15) {
            prefix += " ".repeat(15 - prefix.length());
        }
        s.print(prefix);
        for (int i = start, count = 0 ; i <= limit ; i ++, count++) {
            if (count % 5 == 0) {
                s.print("  ");
            }
            s.print(getByteString(buffer[i]) + ' ');
        }
        s.println();
    }

    private static String getByteString(byte b) {
        int v = b & 0xFF;
        String res;
        if (v > 127 || ASCII.isCtrl(b)) {
            res = "0x" + Integer.toHexString(v);
        } else {
            res = "'" + (char)b + "'";
        }
        if (res.length() < 4) {
            res += " ".repeat(4 - res.length());
        }
        return res;
    }

    protected static void printField(String name, Object value) {
        System.err.println("    " + name + ": " + value);
    }
}
