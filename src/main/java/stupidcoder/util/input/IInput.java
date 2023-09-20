package stupidcoder.util.input;

import java.nio.charset.StandardCharsets;

public interface IInput {
    boolean isOpen();

    boolean available();

    /**
     * 读取一个字节
     * @return 下一个字节
     * @throws InputException 在无法读取或者词素长度超过限制时抛出
     */
    int read();

    void skip(int count);

    default int readUnsigned() {
        return read() & 0xFF;
    }

    boolean hasNext();

    /**
     * 获取一段字节，调用{@link IInput#markLexemeStart()}标记起点
     * @return 字节组成的UTF-8字符串
     */
    String lexeme();

    /**
     * 获取一段字节，调用{@link IInput#markLexemeStart()}标记起点
     * @return 字节数组
     */
    byte[] bytesLexeme();

    void markLexemeStart();

    /**
     * 回退一个字节
     * @return 回退过程中经过的最后一个字节（调用{@link IInput#read()}后得到的字节）
     * @throws InputException 回退失败
     */
    int retract();

    default int retract(int count) {
        if (count <= 0) {
            return -1;
        }
        count--;
        for (int i = 0 ; i < count ; i ++) {
            retract();
        }
        return retract();
    }

    default String readUtfChar() {
        byte[] data;
        int b1 = readUnsigned();
        switch (b1 >> 4) {
            case 0, 1, 2, 3, 4, 5, 6, 7 -> data = new byte[]{(byte) b1};
            case 12, 13 -> {
                //110x xxxx 10xx xxxx
                int b2 = readUnsigned();
                if ((b2 & 0xC0) != 0x80) {
                    throw new InputException("malformed input:" + Integer.toBinaryString((b1 << 8) | b2));
                }
                data = new byte[] {(byte) b1, (byte) b2};
            }
            case 14 -> {
                //1110 xxxx 10xx xxxx 10xx xxxx
                int b2 = readUnsigned();
                int b3 = readUnsigned();
                if ((b2 & 0xC0) != 0x80 || (b3 & 0xC0) != 0x80) {
                    throw new InputException("malformed input:" +
                            Integer.toBinaryString((b1 << 16) | (b2 << 8) | b3));
                }
                data = new byte[]{(byte) b1, (byte) b2, (byte) b3};
            }
            default -> throw new InputException("malformed input:" + Integer.toBinaryString(b1));
        }
        return new String(data, StandardCharsets.UTF_8);
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

    default void find(int ch) {
        while (available()) {
            if (read() == ch) {
                break;
            }
        }
    }

    /**
     * 不断读取字符，直到遇到目标字符
     * @param chs 目标字符，位于[0, 128)外的会被忽略
     * @return 找到的那个字符，没找到返回-1
     */
    default int find(int ... chs) {
        BitClass clazz = new BitClass();
        for (int ch : chs) {
            clazz.add(ch);
        }
        return find(clazz);
    }


    default int find(BitClass clazz) {
        while (available()) {
            int b = read();
            if (clazz.accept(b)) {
                return b;
            }
        }
        return -1;
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
