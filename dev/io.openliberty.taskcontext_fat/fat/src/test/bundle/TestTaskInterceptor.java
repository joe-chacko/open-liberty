package test.bundle;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskInterceptor;
import com.ibm.wsspi.threading.WithContext;

@Component
public class TestTaskInterceptor implements TaskInterceptor {
    private static final AtomicInteger activatedCount = new AtomicInteger();
    private final int instance = activatedCount.incrementAndGet();
    private static final AtomicReference<TestTaskInterceptor> lastInstance = new AtomicReference<>();
    public static final ThreadLocal<TaskContext> lastContext = new ThreadLocal<>();

    public static TestTaskInterceptor getInstance() {
        return lastInstance.get();
    }

    @Activate
    protected void activate() {
        System.out.println("### Activate TestService#" + instance);
        lastInstance.set(this);
    }

    @Deactivate
    protected void deactivate() {
        lastInstance.compareAndSet(this, null);
    }

    private static Runnable convertCallableToRunnable(Callable<?> c) {
        return () -> {
            try {
                c.call();
            } catch (final Exception e) {
                throw new Error(e);
            }
        };
    }

    private static Callable<Void> convertRunnableToCallable(Runnable r) {
        return () -> {
            r.run();
            return null;
        };
    }

    @Override
    public Runnable wrapWithContext(Runnable r, WithContext wc) {

        final Callable<Void> callable = convertRunnableToCallable(r);
        final Callable<Void> wrappedCallable = wrapWithContext(callable, wc);
        return convertCallableToRunnable(wrappedCallable);

        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'wrapWithContext'");
    }

    @Override
    public <T> Callable<T> wrapWithContext(Callable<T> c, WithContext wc) {
        final Optional<TaskContext> tc = wc.getTaskContext();
        new Throwable("wrapping callable " + c).printStackTrace(System.out);
        return () -> {
            // THREAD 2 - during dispatch
            new Throwable("wrapped callable invoked")
                    .printStackTrace(System.out);
            // stash the task context retrieved earlier
            // on a thread local so the test can access it
            tc.ifPresent(lastContext::set);
            try {
                final T t = c.call();
                System.out.println("task returned " + t);
                return t;
            } finally {
                lastContext.remove();
            }
        };
    }
}
