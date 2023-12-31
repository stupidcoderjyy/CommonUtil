package input.compile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.ReflectionUtil;
import stupidcoder.util.input.CompilerInput;

import java.util.Deque;

public class TestMark {

    @Test
    public void test1() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_mark.txt", 5);
        input.approach('3');
        input.mark();
        input.approach('A');
        input.read();
        Deque<Object[]> markData = ReflectionUtil.getObjectField(input, "markData");
        Assertions.assertTrue(markData.isEmpty());
    }

    @Test
    public void test2() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_mark.txt", 5);
        input.approach('7');
        input.mark();
        input.approach('E');
        input.read();
        Deque<Object[]> markData = ReflectionUtil.getObjectField(input, "markData");
        Assertions.assertTrue(markData.isEmpty());
    }

    @Test
    public void test3() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_mark_2.txt");
        for (int i = 0; i < 31 ; i ++) {
            input.read();
            input.mark();
        }
        long l = System.currentTimeMillis();
        input.mark();
        System.out.println(System.currentTimeMillis() - l);
        input.removeMark();
        input.close();
    }
}
