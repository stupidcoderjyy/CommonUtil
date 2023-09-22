package input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.StringInput;

public class TestReadUtf {

    @Test
    public void test1() {
        StringInput input = new StringInput("你好");
        Assertions.assertEquals("你", input.readUtfChar());
        Assertions.assertEquals("好", input.readUtfChar());
        Assertions.assertEquals("你好", input.capture());
    }
}
