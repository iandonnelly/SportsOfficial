package example.example.com.sportsofficial.presentation.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import example.example.com.sportsofficial.domain.executor.ThreadExecutor;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */
public class JobExecutor implements ThreadExecutor {
    private static final int INITIAL_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> mWorkQueue;
    private final ThreadPoolExecutor mThreadPoolExecutor;
    private final ThreadFactory mThreadFactory;

    public JobExecutor() {
        mWorkQueue = new LinkedBlockingQueue<>();
        mThreadFactory = new JobThreadFactory();
        mThreadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mWorkQueue, mThreadFactory);
    }

    /**
     * {@inheritDoc}
     *
     * @param runnable The class that implements {@link Runnable} interface.
     */
    @Override public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    /**
     *
     */
    private static class JobThreadFactory implements ThreadFactory {
        private static final String THREAD_NAME = "android_";
        private int counter = 0;

        @Override public Thread newThread(Runnable runnable) {
            return new Thread(runnable, THREAD_NAME + counter);
        }
    }
}
