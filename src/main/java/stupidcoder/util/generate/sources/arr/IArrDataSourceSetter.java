package stupidcoder.util.generate.sources.arr;

import stupidcoder.util.generate.sources.SourceCached;

@FunctionalInterface
public interface IArrDataSourceSetter {
    void setSource(SourceCached src, String key);
}
