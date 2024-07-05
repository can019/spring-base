package com.example.base.test.util;

import com.example.base.test.util.stopwatch.StopWatchUtil;
import org.springframework.test.context.TestContext;
import org.springframework.util.StopWatch;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParallelTestTimeExecutionListener extends AbstractTestTimeExecutionListener {

    public static final ThreadLocal<StopWatch> threadLocalStopWatch =
            ThreadLocal.withInitial(StopWatch::new);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private final Date startedTime = new Date();

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        super.afterTestMethod(testContext);
        StopWatch stopWatch = ParallelTestTimeExecutionListener.threadLocalStopWatch.get();
        StopWatchUtil.exportCsv(stopWatch, exportCsvPathResolver(testContext));
        threadLocalStopWatch.remove();
    }

    private String exportCsvPathResolver(TestContext testContext){
        String packageName = testContext.getTestClass().getPackageName();
        String className = testContext.getTestClass().getSimpleName();
        String csvFilePath = String.join("/",
                "./test/reports",
                packageName,
                className,
                dateFormat.format(startedTime),
                testContext.getTestMethod().getName()) + ".csv";

        return csvFilePath;
    }
}