package com.example.base.test.util.stopwatch;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.StopWatch;

import java.util.UUID;

public class ParallelTestTimeExecutionListener extends AbstractTestExecutionListener {

    public static final ThreadLocal<StopWatch> threadLocalStopWatch =
            ThreadLocal.withInitial(StopWatch::new);

    public static final UUID randomUUID = UUID.randomUUID();

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        super.beforeTestClass(testContext);
        System.out.println("Running test '" + testContext.getTestClass().getSimpleName() + "'...");
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        super.beforeTestMethod(testContext);
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        StopWatch stopWatch = ParallelTestTimeExecutionListener.threadLocalStopWatch.get();
        StopWatchUtil.exportCsv(stopWatch, exportCsvPathResolver(testContext));
        threadLocalStopWatch.remove();
        super.afterTestMethod(testContext);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        super.afterTestClass(testContext);
        System.out.println("The test has been completed" + testContext.getTestClass().getSimpleName());
    }

    private String exportCsvPathResolver(TestContext testContext){
        String packageName = testContext.getTestClass().getPackageName();
        String className = testContext.getTestClass().getSimpleName();
        String csvFilePath = "./test/reports/" + packageName
                + "/" + className + "/" + randomUUID + "/" +testContext.getTestMethod()+".csv";

        return csvFilePath;
    }
}