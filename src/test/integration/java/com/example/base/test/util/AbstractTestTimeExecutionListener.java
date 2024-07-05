package com.example.base.test.util;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.StopWatch;

public abstract class AbstractTestTimeExecutionListener extends AbstractTestExecutionListener {
    private StopWatch totalTaskStopWatch;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        super.beforeTestClass(testContext);
        System.out.println("Running test '" + testContext.getTestClass().getSimpleName() + "'...");
        totalTaskStopWatch = new StopWatch(testContext.getTestClass().getSimpleName()+ " Total");
        totalTaskStopWatch.start("Total");
    }


    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        super.beforeTestMethod(testContext);
        Thread.currentThread().setName(testContext.getTestMethod().getName());
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        super.afterTestClass(testContext);
        totalTaskStopWatch.stop();

        System.out.println("The test in '" + testContext.getTestClass().getSimpleName()+"' has been completed");
        System.out.println(totalTaskStopWatch.prettyPrint());
    }
}
