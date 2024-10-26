package stupidcoder.util.generate.project.cpp;

import stupidcoder.util.generate.Generator;
import stupidcoder.util.generate.sources.SourceFieldString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CProjectBuilder {
    private final Generator root;
    private final String rootScriptPath;
    private final Map<String, Generator> generators = new HashMap<>();
    private final List<ICppProjectAdapter> adapters = new ArrayList<>();

    public CProjectBuilder(String projectName, String rootScriptPath) {
        root = new Generator();
        SourceFieldString src = new SourceFieldString("project", projectName::toUpperCase);
        src.lock();
        root.registerSrc(src);
        this.rootScriptPath = rootScriptPath;
    }

    public void include(String scriptName, Generator g) {
        g.setParent(root);
        generators.put(scriptName, g);
    }

    public void addAdapter(ICppProjectAdapter adapter) {
        adapters.add(adapter);
    }

    public void gen() {
        adapters.forEach(a -> a.build(this));
        generators.forEach((script, g) -> g.loadScript(
                rootScriptPath.isEmpty() ? script : rootScriptPath + "/" + script,
                script));
    }
}
