package com.github.can019.base.api.global.aop.logger;

import net.datafaker.Faker;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.aspectj.lang.Signature;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LayeredArchitectureLogAspectUnitTest {
    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;

    @InjectMocks
    private LayeredArchitectureLogAspect.BaseLogAspect baseLogAspect;

    private Faker faker = new Faker();

    @Test
    public void testDefaultInputLog() {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("MockedMethod()");
        when(joinPoint.getArgs()).thenReturn(new Object[] {"arg1", "arg2"});
        when(joinPoint.getTarget()).thenReturn(new Object());

        LayeredArchitectureLogAspect.defaultTraceInputLog(joinPoint);

        verify(joinPoint).getSignature();
        verify(joinPoint).getArgs();
        verify(joinPoint).getTarget();
    }

    @Test
    public void testDefaultOutputLog() {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("MockedMethod()");
        when(joinPoint.getTarget()).thenReturn(new Object());
        Object returnValue = "MockedReturn";

        LayeredArchitectureLogAspect.defaultTraceOutputLog(joinPoint, returnValue);

        verify(joinPoint).getSignature();
        verify(joinPoint).getTarget();
    }

    @Test
    public void testErrorLog() {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("MockedMethod()");
        String randomExceptionMessage = faker.lorem().sentence();

        RuntimeException exception = spy(new RuntimeException(randomExceptionMessage));

        LayeredArchitectureLogAspect.errorLog(joinPoint, exception);

        verify(joinPoint).getSignature();
        verify(exception).getMessage();
        verify(exception).getStackTrace();
    }
}
