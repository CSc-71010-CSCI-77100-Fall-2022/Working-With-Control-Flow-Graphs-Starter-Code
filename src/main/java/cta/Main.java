package cta;

import static com.ibm.wala.ipa.callgraph.impl.Util.makeMainEntrypoints;
import static com.ibm.wala.ipa.callgraph.impl.Util.makeZeroCFABuilder;
import static com.ibm.wala.types.ClassLoaderReference.Application;
import static com.ibm.wala.util.config.AnalysisScopeReader.makeJavaBinaryAnalysisScope;

import java.io.File;

import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.AnalysisCache;
import com.ibm.wala.ipa.callgraph.AnalysisCacheImpl;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.SSAInstruction;

public class Main {

	/**
	 * Location of the file with a list of packages to exclude from the
	 * analysis.
	 */
	private static final String EXCLUSIONS_FILE_PATH_NAME = "Java60RegressionExclusions.txt";

	private static void checkArgs(String[] args) {
		if (args.length < 1) {
			System.err.println("Usage: java " + Main.class.getName() + " [class path]");
			System.exit(1);
		}
	}

	public static void main(String[] args) throws Exception {
		checkArgs(args);

		// the class path to analyze should be the first argument.
		String classPath = args[0];

		File exclusionsFile = new File(EXCLUSIONS_FILE_PATH_NAME);
		AnalysisScope scope = makeJavaBinaryAnalysisScope(classPath, exclusionsFile);

		ClassHierarchy hierarchy = ClassHierarchyFactory.make(scope);
		Iterable<Entrypoint> entrypoints = makeMainEntrypoints(scope, hierarchy);

		AnalysisOptions options = new AnalysisOptions(scope, entrypoints);
		AnalysisCache cache = new AnalysisCacheImpl();

		CallGraphBuilder<?> builder = makeZeroCFABuilder(options, cache, hierarchy, scope);
		CallGraph graph = builder.makeCallGraph(options, null);

		graph.forEach(node -> {
			// get the IR for the call graph "node." Each node in this graph
			// represents a method.
			IR ir = node.getIR();

			if (ir != null) {
				// the method whose body the IR represents.
				IMethod method = ir.getMethod();

				// only process methods from classes in the "application" space
				// (as opposed to the libraries).
				if (method.getDeclaringClass().getClassLoader().getReference().equals(Application)) {
					System.out.println("Processing application method: " + method);
					checkCFG(ir);
				}
			}
		});

		// TODO: Output information for all CFGs here.
	}

	private static void checkCFG(IR ir) {
		// TODO: Enter your CFG processing code here.
	}
}
