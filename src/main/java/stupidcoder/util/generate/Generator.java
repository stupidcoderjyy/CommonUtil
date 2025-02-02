package stupidcoder.util.generate;

import stupidcoder.util.Config;
import stupidcoder.util.generate.parse.InternalParsers;
import stupidcoder.util.generate.parse.Parser;
import stupidcoder.util.generate.parse.parsers.ParserUnitArraySetter;
import stupidcoder.util.input.BitClass;
import stupidcoder.util.input.CompilerInput;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Generator {
    protected static final Map<String, Parser> DEFAULT_UNIT_PARSERS = new HashMap<>();
    protected final Map<String, Source> sources = new HashMap<>();
    protected final Map<String, Parser> customUnitParsers = new HashMap<>();
    protected Generator parent;

    static {
        DEFAULT_UNIT_PARSERS.put("f", InternalParsers.UNIT_FORMAT);
        DEFAULT_UNIT_PARSERS.put("format", InternalParsers.UNIT_FORMAT);
        DEFAULT_UNIT_PARSERS.put("c", InternalParsers.UNIT_COMPLEX);
        DEFAULT_UNIT_PARSERS.put("complex", InternalParsers.UNIT_COMPLEX);
        DEFAULT_UNIT_PARSERS.put("const", InternalParsers.UNIT_CONST);
        DEFAULT_UNIT_PARSERS.put("r", InternalParsers.UNIT_REPEAT);
        DEFAULT_UNIT_PARSERS.put("repeat", InternalParsers.UNIT_REPEAT);
        DEFAULT_UNIT_PARSERS.put("s", InternalParsers.UNIT_SWITCH);
        DEFAULT_UNIT_PARSERS.put("switch", InternalParsers.UNIT_SWITCH);
        DEFAULT_UNIT_PARSERS.put("arr", new ParserUnitArraySetter());
    }

    public Generator(Generator parent) {
        this.parent = parent;
    }

    public Generator() {
        this(null);
    }

    public void setParent(Generator parent) {
        this.parent = parent;
    }

    public void registerParser(String name, Parser parser) {
        customUnitParsers.put(name, parser);
    }

    public void registerSrc(Source src) {
        sources.put(src.name, src);
    }

    public void loadScript(String scriptFile, String targetFile) {
        sources.forEach((name, src) -> src.lock());
        try (CompilerInput input = CompilerInput.fromResource(Config.resourcePath(scriptFile));
             FileWriter writer = new FileWriter(Config.outputPath(targetFile), StandardCharsets.UTF_8)){
            loadScript(input, writer);
        } catch (Exception e) {
            System.err.println("failed to output file:" + targetFile + "(" + e.getMessage() + ")");
        }
        sources.forEach((name, src) -> src.destroy());
    }

    public void loadScript(CompilerInput input, FileWriter writer) throws Exception{
        BitClass space = BitClass.of(' ', '\t');
        while (true) {
            input.mark();
            input.skip(space);
            if (!input.available()) {
                input.mark();
                writer.write(input.capture());
                return;
            }
            switch (input.read()) {
                case '$' -> {
                    input.retract();
                    input.removeMark();
                    OutUnit unit = InternalParsers.UNIT.parse(this, input, null);
                    unit.writeAll(writer, null);
                    unit.close();
                    input.skipLine();
                }
                case '\r' -> {
                    input.removeMark();
                    writer.write("\r\n");
                    input.skipLine();
                }
                default -> {
                    int b = input.approach('\r');
                    input.mark();
                    writer.write(input.capture());
                    if (b < 0) {
                        return;
                    }
                    writer.write("\r\n");
                    input.skipLine();
                }
            }
        }
    }

    public Source getSrc(String name) {
        Generator g = this;
        Source src;
        while (g != null) {
            src = g.sources.get(name);
            if (src != null) {
                return src;
            }
            g = g.parent;
        }
        return null;
    }

    public Parser getUnitParser(String name) {
        Parser p = DEFAULT_UNIT_PARSERS.get(name);
        if (p != null) {
            return p;
        }
        Generator g = this;
        while (g != null) {
            p = g.customUnitParsers.get(name);
            if (p != null) {
                return p;
            }
            g = g.parent;
        }
        return null;
    }
}
