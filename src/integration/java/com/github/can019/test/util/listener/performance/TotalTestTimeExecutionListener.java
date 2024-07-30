package com.github.can019.test.util.listener.performance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.StopWatch;

@Slf4j
public class TotalTestTimeExecutionListener extends AbstractTestExecutionListener {
    private StopWatch totalTaskStopWatch;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        log.info("Running test '{}'...",testContext.getTestClass().getSimpleName());
        totalTaskStopWatch = new StopWatch(testContext.getTestClass().getSimpleName()+ " Total");
        totalTaskStopWatch.start("Total");
        super.beforeTestClass(testContext);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        super.afterTestClass(testContext);
        totalTaskStopWatch.stop();

        log.info("The test in '{}' has been completed",testContext.getTestClass().getSimpleName());
        log.info(totalTaskStopWatch.prettyPrint());
    }
}
