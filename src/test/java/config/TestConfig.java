package config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stupidcoder.util.Config;

import java.io.File;

public class TestConfig {

    @Test
    public void testRegister() {
        int f1 = Config.register(Config.INT_T, 10);
        int f2 = Config.register(Config.INT_T, 20);
        int f3 = Config.register(Config.BOOL_T, true);
        int f4 = Config.register(Config.BOOL_T, false);
        int f5 = Config.register(Config.STRING_T, "aaaa");
        int f6 = Config.register(Config.STRING_T, "bbbb");
        Assertions.assertThrows(IllegalArgumentException.class, () -> Config.register(Config.STRING_T, 2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Config.register(10, 2));
        Assertions.assertEquals(10, Config.getInt(f1));
        Assertions.assertEquals(20, Config.getInt(f2));
        Assertions.assertTrue(Config.getBool(f3));
        Assertions.assertFalse(Config.getBool(f4));
        Assertions.assertEquals("aaaa", Config.getString(f5));
        Assertions.assertEquals("bbbb", Config.getString(f6));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Config.getBool(f1));
        Config.register(Config.PATH_T, "build/out/test");
        Assertions.assertTrue(new File("build/out/test").exists());
    }
}
