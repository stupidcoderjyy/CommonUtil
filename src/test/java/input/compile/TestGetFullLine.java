package input.compile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.input.CompilerInput;

public class TestGetFullLine {

    @Test
    public void test1() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_get_full_line.txt");
        Assertions.assertEquals("0123456789", input.getFullLine());
    }

    @Test
    public void test2() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_get_full_line.txt");
        input.skipLine();
        Assertions.assertEquals("01234", input.getFullLine());
    }

    @Test
    public void test3() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_get_full_line.txt");
        input.skipLine();
        input.retract(5); // \n
        Assertions.assertEquals("0123456789", input.getFullLine());
    }
}
