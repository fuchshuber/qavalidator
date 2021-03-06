/* ----------------------------------------------------------------------------------------------------
 * QAV Architecture Definition
 * ---------------------------------------------------------------------------------------------------- */

architecture(name: "T-View", prefix: "tview", reflexMLversion: "1.0") {

    includes "de.qaware.qav.**"
    excludes "de.qaware.qav.doc.**"

    component("Main") {
        api "de.qaware.qav.app.QavMain", "de.qaware.qav.app.QavApp"
    }

    component("QAV_Runner") {
        api "de.qaware.qav.runner.**"
    }

    component("AnalysisDSL") {
        api "de.qaware.qav.analysis.dsl.api.**"
        api "de.qaware.qav.analysis.dsl.model.**"
        impl "de.qaware.qav.analysis.dsl.impl.**"
    }

    component("AnalysisPlugins") {
        api "de.qaware.qav.analysis.plugins.**"

        uses "Graph.filter", "Graph.base"
    }

    component("ArchitectureDSL") {
        api "de.qaware.qav.architecture.dsl.api.**"
        api "de.qaware.qav.architecture.dsl.model.**"
        impl "de.qaware.qav.architecture.dsl.impl.**"
    }

    component("Architecture") {
        api "de.qaware.qav.architecture.checker.*"
        api "de.qaware.qav.architecture.factory.*"
        api "de.qaware.qav.architecture.nodecreator.*"
        api "de.qaware.qav.architecture.tagger.*"

        uses "Graph.base"
    }

    component("Graph") {
        component("Graph.base") {
            api "de.qaware.qav.graph.api.**"
            impl "de.qaware.qav.graph.impl.**"
        }

        component("Graph.alg") {
            api "de.qaware.qav.graph.alg.**"
            usesImpl "Graph.base" // Exception from the rule: some algorithms expect a specific graph implementation to work
        }
        component("Graph.factory") {
            api "de.qaware.qav.graph.factory.**"
            usesImpl "Graph.base" // Factory pattern: the factory has to instantiate a specific implementation class
        }
        component("Graph.filter") {api "de.qaware.qav.graph.filter.**"}
        component("Graph.io") {api "de.qaware.qav.graph.io.**"}
        component("Graph.index") {api "de.qaware.qav.graph.index.**"}
    }

    component("Input") {
        component("JavaInput") {api "de.qaware.qav.input.javacode.**"}
        component("MavenInput") {api "de.qaware.qav.input.maven.**"}
        component("TypescriptInput") {api "de.qaware.qav.input.typescript.**"}

        uses "Graph.base"
    }

    component("Server") {
        api "de.qaware.qav.server.**"
        uses "Graph.base", "Graph.io", "Graph.index"
    }

    component("Util") {
        api "de.qaware.qav.util.**"
    }

    component("Visualization") {
        api "de.qaware.qav.visualization.**"
        uses "Graph.filter", "Graph.base", "Graph.factory"
    }

}
