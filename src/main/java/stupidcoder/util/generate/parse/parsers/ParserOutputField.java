package stupidcoder.util.generate.parse.parsers;

import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.OutUnit;
import stupidcoder.util.generate.OutUnitField;
import stupidcoder.util.generate.parse.InternalParsers;
import stupidcoder.util.generate.parse.Parser;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class ParserOutputField implements Parser {

    @Override
    public OutUnit parse(Generator g, CompilerInput input, OutUnit raw) throws CompileException {
        checkNext(input, '%');
        input.mark(); // 1 capture begin
        input.mark(); // 2 err
        input.mark(); // 3 err
        if (input.approach('\r', ':', ',', '}') != ':') {
            input.mark(); // 4 err end
            throw input.errorMarkToMark("missing entry name");
        }
        input.removeMark(); // 2
        input.mark(); // 3 capture end
        String name = input.capture(); //1
        OutUnitField f = raw.fields.get(name);
        if (f == null) {
            input.retract();
            throw input.errorMarkToForward("unknown field name"); //0
        }
        input.read();
        f.value = InternalParsers.UNIT.parse(g, input, null);
        return raw;
    }
}
