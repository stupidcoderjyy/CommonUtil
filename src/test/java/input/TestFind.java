package input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.StringInput;

public class TestFind {

    @Test
    public void test1() {
        StringInput input = new StringInput("abcdef");
        input.find('c', 'd');
        Assertions.assertEquals('d', input.read());
    }

    @Test
    public void test2() {
        StringInput input = new StringInput("ab我达瓦达瓦def");
        input.find('c', -1, 'd');
        Assertions.assertEquals('e', input.read());
    }
}
