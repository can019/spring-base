package com.example.base.test.util;

import com.example.base.test.util.stopwatch.StopWatchUtil;
import org.springframework.test.context.TestContext;
import org.springframework.util.StopWatch;

import java.util.UUID;

public class TestTimeExecutionListener extends AbstractTestTimeExecutionListener {

    private StopWatch stopWatch;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        super.beforeTestClass(testContext);
        stopWatch = new StopWatch(testContext.getTestClass().getSimpleName());
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        super.beforeTestMethod(testContext);
        stopWatch.start(testContext.getTestMethod().getName());
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        if (stopWatch.isRunning()) {
            stopWatch.stop();
        }
        super.afterTestMethod(testContext);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        super.afterTestClass(testContext);
        System.out.println("Test '" + testContext.getTestClass().getSimpleName() + "' finished after " + stopWatch.getTotalTimeSeconds() + " seconds.");
    }

    private String exportCsvPathResolver(TestContext testContext){
        String packageName = testContext.getTestClass().getPackageName();
        String className = testContext.getTestClass().getSimpleName();
        String csvFilePath = "./test/reports/" + packageName
                + "/" + className + "/"+ UUID.randomUUID()+".csv";

        return csvFilePath;
    }
}