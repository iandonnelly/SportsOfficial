package example.example.com.sportsofficial.presentation.thread;


import android.os.Handler;
import android.os.Looper;

import example.example.com.sportsofficial.domain.executor.PostExecutionThread;

/**
 * MainThread (UI Thread) implementation based on a Handler instantiated with the main
 * application Looper.
 */
public class UIThread implements PostExecutionThread {
    private final Handler mHandler;

    public UIThread() {
        mHandler = new Handler(Looper.getMainLooper());
    }
    /**
     * Causes the Runnable r to be added to the message queue.
     * The runnable will be run on the main thread.
     *
     * @param runnable {@link Runnable} to be executed.
     */
    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}
