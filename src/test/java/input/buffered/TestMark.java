package input.buffered;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.BufferedInput;
import stupidcoder.util.input.readers.StringByteReader;

public class TestMark {

    @Test
    public void test_basic() {
        BufferedInput input = new BufferedInput(new StringByteReader("0123456789ABCDEF"));
        input.skip(2);
        input.mark();
        input.skip(1);
        input.mark();
        Assertions.assertEquals("2", input.capture());
        input.close();
    }

    @Test
    public void test_remove_a() {
        BufferedInput input = new BufferedInput(new StringByteReader("0123ABCD0123"), 4);
        input.skip(1);
        input.mark();
        input.find('2');
        input.read();   //加载B
        input.mark();
        input.approach('D');
        input.read();   //加载A，清除
        input.mark();
        input.approach('2');
        Assertions.assertEquals("ABCD", input.capture());
        input.close();
    }

    @Test
    public void test_remove_b() {
        BufferedInput input = new BufferedInput(new StringByteReader("0123ABCD0123ABCD"), 4);
        input.approach('A');
        input.mark();
        input.approach('D');
        input.read(); //加载B
        input.approach('3');
        input.read(); //加载A，清除
        input.mark();
        input.find('D');
        input.mark();
        Assertions.assertEquals("ABCD", input.capture());
        input.close();
    }
}
