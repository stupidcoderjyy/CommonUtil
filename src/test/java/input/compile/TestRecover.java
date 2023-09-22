package input.compile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.ReflectionUtil;
import stupidcoder.util.input.CompilerInput;

import java.util.Deque;

public class TestRecover {

    @Test
    public void test1() {
        CompilerInput input = CompilerInput.fromResource("/input/compile/test_recover.txt");
        input.approach('A');
        input.mark();
        input.approach('D');
        input.recover(true);
        Deque<int[]> markData = ReflectionUtil.getObjectField(input, "markData");
        Deque<Integer> columnSizes = ReflectionUtil.getObjectField(input, "columnSizes");
        Deque<Integer> rowBegin = ReflectionUtil.getObjectField(input, "rowBegin");
        Assertions.assertEquals(1, markData.size());
        Assertions.assertTrue(columnSizes.isEmpty());
        Assertions.assertEquals(0, rowBegin.getFirst());
    }
}
