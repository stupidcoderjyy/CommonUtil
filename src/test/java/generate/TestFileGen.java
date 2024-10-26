package generate;

import org.junit.jupiter.api.Test;
import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.sources.SourceCached;
import stupidcoder.util.generate.sources.SourceFieldInt;
import stupidcoder.util.generate.sources.SourceFieldString;

import java.util.UUID;

public class TestFileGen {

    @Test
    public void test1() {
        Generator g = new Generator();
        SourceCached src = new SourceCached("src");
        for (int i = 0 ; i < 40 ; i ++) {
            src.writeString(UUID.randomUUID().toString());
        }
        g.registerSrc(new SourceFieldInt("count", () -> 40));
        g.registerSrc(src);
        g.loadScript("generate/1.txt", "1.txt");
    }

    @Test
    public void test2() {
        Generator g = new Generator();
        SourceCached src = new SourceCached("src");
        for (int i = 0 ; i < 40 ; i ++) {
            src.writeString(UUID.randomUUID().toString());
        }
        g.registerSrc(new SourceFieldInt("count", () -> 40));
        g.registerSrc(src);
        g.loadScript("generate/2.txt", "2.txt");
    }

    @Test
    public void test3() {
        Generator g = new Generator();
        SourceFieldString src = new SourceFieldString("str", () -> UUID.randomUUID().toString());
        g.registerSrc(src);
        g.loadScript("generate/3.txt", "3.txt");
    }
}
