package com.stupidcoder.util.input;

import java.io.Closeable;

/**
 * 用于词法分析的输入系统
 * @author stupid_coder_jyy
 */
public interface IInput extends Closeable {

    /**
     * 判断输入系统是否启动
     * @return 启动返回true
     */
    boolean isOpen();

    /**
     * 是否可以继续向后取字符，即是否可以调用 {@link IInput#read()}
     * @return 可以则返回true
     */
    boolean available();

    /**
     * 读取下一个字节
     * @return 字节
     */
    int read();

    /**
     * 跳过几个字节
     * @param count 跳过的字节数
     */
    void skip(int count);

    /**
     * 读取一个无符号字节
     * @return 无符号字节
     */
    int readUnsigned();

    /**
     * 是否还可能读入字符（是否到达输入流结尾）
     * @return 不能再读入字符返回true
     */
    boolean hasNext();

    /**
     * 获得完整的词素，同时更新词素起点
     * @return 词素
     */
    String lexeme();

    /**
     * 手动更新词素起点
     */
    void markLexemeStart();

    /**
     * 回退若干字符
     * @param count 回退的个数
     */
    void retract(int count);

    /**
     * 关闭输入系统
     */
    void close();

    /**
     * 读取一个UTF字符
     * @return UTF编码
     */
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

    /**
     * 回退一个字符
     */
    default void retract() {
        retract(1);
    }

    /**
     * 跳过空格和制表符
     */
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
