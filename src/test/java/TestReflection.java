import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.ReflectionUtil;

public class TestReflection {

    @Test
    public void testGetField() {
        TestClazz obj = new TestClazz();
        int[] arr1 = ReflectionUtil.getObjectField(obj, "arr1");
        int[] arr2 = ReflectionUtil.getObjectField(obj, "arr2");
        Assertions.assertNotNull(arr1);
        Assertions.assertNotNull(arr2);
        Assertions.assertEquals(1, arr1[0]);
        Assertions.assertEquals(1, arr2[0]);
    }

    private static class TestClazz {
        private final int[] arr1 = new int[]{1,2,3};
        private static final int[] arr2 = new int[]{1,2,3};
    }
}
