package input.compile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.ReflectionUtil;
import stupidcoder.util.input.CompilerInput;

import java.util.Deque;

public class TestRetract {

    @Test
    public void test1() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_retract.txt", 5);
        input.approach('A');
        Assertions.assertEquals('\r', input.retract(2));
    }

    @Test
    public void test2() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_retract.txt", 5);
        input.approach('\n');
        input.read();
        input.approach('\n');
        input.read();
        input.approach('A');
        input.retract(2);
        Deque<Integer> rowBegin = ReflectionUtil.getObjectField(input, "rowBegin");
        Assertions.assertEquals(5, rowBegin.getFirst());
    }

    @Test
    public void test3() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_retract.txt", 5);
        input.approach('D');
        input.retract(2);
        Deque<Integer> rowBegin = ReflectionUtil.getObjectField(input, "rowBegin");
        Assertions.assertEquals(0, rowBegin.getFirst());
    }

    @Test
    public void test4() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_retract.txt", 5);
        input.find('B');
        input.mark();
        input.retract();
        Deque<int[]> markData = ReflectionUtil.getObjectField(input, "markData");
        Assertions.assertTrue(markData.isEmpty());
    }
}
