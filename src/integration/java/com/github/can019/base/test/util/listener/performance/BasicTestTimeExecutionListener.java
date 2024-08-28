package com.github.can019.base.test.util.listener.performance;

import com.github.can019.base.test.util.report.StopWatchReporter;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.StopWatch;

import static com.github.can019.base.test.util.report.StopWatchReporter.exportReport;

/**
 * Non thread safe
 * 자동으로 각 test method의 수행 시간을 측정, csv로 export
 * `@Execution(value = ExecutionMode.SAME_THREAD)`에서만 사용 가능
 */
public class BasicTestTimeExecutionListener extends AbstractTestExecutionListener {
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

        exportReport(stopWatch, "total", testContext.getTestClass(),  StopWatchReporter.ReportType.CSV);
    }
}
