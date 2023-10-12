package range;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.OpenRange;

public class TestUnion {

    @Test
    public void test0() {
        Assertions.assertEquals(
                "(9,12)",
                build1(10, 10,12).toString());
        Assertions.assertEquals(
                "(8,10)U(10,12)",
                build1(9, 10,12).toString());
        Assertions.assertEquals(
                "(10,12)U(12,14)",
                build1(13, 10,12).toString());
        Assertions.assertEquals(
                "(14,17)U(32,34)",
                build1(15, 15,17,32,34).toString());
        Assertions.assertEquals(
                "(15,17)U(18,20)U(32,34)",
                build1(19, 15,17,32,34).toString());
        Assertions.assertEquals(
                "(15,17)U(31,34)",
                build1(32, 15,17,32,34).toString());
        Assertions.assertEquals(
                "(15,17)U(32,35)",
                build1(34, 15,17,32,34).toString());
        Assertions.assertEquals(
                "(15,17)U(32,34)U(39,41)",
                build1(40, 15,17,32,34).toString());
        Assertions.assertEquals(
                "(11,13)U(15,17)U(32,34)",
                build1(12, 15,17,32,34).toString());
    }

    private OpenRange build1(int data, int ... init) {
        OpenRange r = OpenRange.empty();
        for (int i = 0 ; i < init.length ; i += 2) {
            r.union(init[i], init[i + 1]);
        }
        return r.union(data);
    }
}
