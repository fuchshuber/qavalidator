package de.qaware.qav.app;

import de.qaware.qav.runner.QAvalidator;
import de.qaware.qav.runner.QAvalidatorConfig;
import de.qaware.qav.runner.QAvalidatorResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.List;

/**
 * @author QAware GmbH
 */
@Component
public class QavApp implements ApplicationRunner {

    private final PrintStream out = System.out;

    @Value("${analysis}")
    private String analysisFileName;

    @Value("${outputDir}")
    private String outputDir;


    @Override
    public void run(ApplicationArguments args) {
        QAvalidatorConfig config = getConfig(args);
        QAvalidatorResult result = new QAvalidator().runAnalysis(config);
        reportErrors(result);
    }

    /**
     * Set up the configuration for the QAvalidator run.
     * <p>
     * Uses the properties <tt>analysis</tt> and <tt>outputDir</tt>, which have defaults and may be overwritten with a
     * profile or via command line.
     *
     * @param args command line arguments, used for the input directories given as non-option arguments.
     * @return the {@link QAvalidatorConfig}
     */
    private QAvalidatorConfig getConfig(ApplicationArguments args) {
        QAvalidatorConfig config = new QAvalidatorConfig();
        config.setAnalysisFilename(this.analysisFileName);

        if (this.outputDir != null) {
            config.setOutputDir(this.outputDir);
        }

        List<String> inputDirs = args.getNonOptionArgs();
        if (inputDirs != null && !inputDirs.isEmpty()) {
            config.setInputDirs(inputDirs);
        }
        return config;
    }

    /**
     * Report errors, if any occurred.
     *
     * @param result the result to report.
     */
    private void reportErrors(QAvalidatorResult result) {
        if (result.isFailedWithException()) {
            out.println("===== Error in analysis =====");
            out.println(result.getExceptionMessage());
        }
        if (!result.getFailedSteps().isEmpty()) {
            out.println("===== Failed steps: =====");
            out.println(result.getFailedSteps().toString());
        }
    }
}
