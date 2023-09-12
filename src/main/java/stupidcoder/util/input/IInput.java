package stupidcoder.util.input;


public interface IInput {
    boolean isOpen();

    boolean available();

    int read();

    void skip(int count);

    int readUnsigned();

    boolean hasNext();

    String lexeme();

    void markLexemeStart();

    void retract(int count);

    void close();

    default String readUtfChar()  {
        markLexemeStart();
        int b1 = readUnsigned();
        switch (b1 >> 4) {
            case 0, 1, 2, 3, 4, 5, 6, 7 -> {}
            case 12, 13 -> {
                //110x xxxx 10xx xxxx
                int b2 = readUnsigned();
                if ((b2 & 0xC0) != 0x80) {
                    throw new InputException("malformed input:" + Integer.toBinaryString((b1 << 8) | b2));
                }
            }
            case 14 -> {
                //1110 xxxx 10xx xxxx 10xx xxxx
                int b2 = readUnsigned();
                int b3 = readUnsigned();
                if ((b2 & 0xC0) != 0x80 || (b3 & 0xC0) != 0x80) {
                    throw new InputException("malformed input:" +
                            Integer.toBinaryString((b1 << 16) | (b2 << 8) | b3));
                }
            }
            default -> throw new InputException("malformed input:" + Integer.toBinaryString(b1));
        }
        return lexeme();
    }

    default void retract() {
        retract(1);
    }

    default void skipSpaceAndTab() {
        int nb;
        while (available()) {
            nb = read();
            if (nb != ' ' && nb != '\t') {
                retract();
                markLexemeStart();
                return;
            }
        }
    }


    default void skipSpaceTabLineBreak(){
        int nb;
        while (available()) {
            nb = read();
            if (nb == '\r') {
                read();
                continue;
            }

            if (nb != ' ' && nb != '\t') {
                retract();
                markLexemeStart();
                return;
            }
        }
    }

    default void checkOpen() {
        if (!isOpen()) {
            throw new InputException("closed");
        }
    }

    default void checkAvailable() {
        if (!available()) {
            throw new InputException("not available");
        }
    }
}
