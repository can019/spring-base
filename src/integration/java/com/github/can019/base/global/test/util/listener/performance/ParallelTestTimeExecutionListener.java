package com.github.can019.base.global.test.util.listener.performance;

import com.github.can019.base.global.test.util.report.StopWatchReporter;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.can019.base.global.test.util.report.StopWatchReporter.*;

public class ParallelTestTimeExecutionListener extends AbstractTestExecutionListener {
    private static final ThreadLocal<StopWatch> threadLocalStopWatch =
            ThreadLocal.withInitial(StopWatch::new);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private final Date startedTime = new Date();

    public static StopWatch getStopWatch(){
        return threadLocalStopWatch.get();
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        super.beforeTestMethod(testContext);
        Thread.currentThread().setName(testContext.getTestMethod().getName());
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        super.afterTestMethod(testContext);
        StopWatch stopWatch = this.getStopWatch();
        threadLocalStopWatch.remove();

        exportReport(stopWatch,
                String.join("/","parallel",testContext.getTestMethod().getName()),
                testContext.getTestClass(),
                StopWatchReporter.ReportType.CSV);
    }
}
