package stupidcoder.util.generate.parse.parsers;

import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.OutUnit;
import stupidcoder.util.generate.outunit.SwitchOut;
import stupidcoder.util.generate.parse.InternalParsers;
import stupidcoder.util.generate.parse.Parser;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class ParserUnitSwitch implements Parser {
    @Override
    public OutUnit parse(Generator g, CompilerInput input, OutUnit raw) throws CompileException {
        SwitchOut switchOut = new SwitchOut();
        while (true) {
            switch (approachNext(input)) {
                case '$', '"' -> switchOut.units.add(InternalParsers.UNIT.parse(g, input, null));
                default -> InternalParsers.ARG_COMMON_OUTPUT_CONFIG.parse(g, input, switchOut);
            }
            if (checkArgInterval(input)) {
                return switchOut;
            }
        }
    }
}
