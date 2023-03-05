package net.royalguardians.PhoenixID.database;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseThread extends Thread {

    private BlockingQueue<Runnable> runnables = new LinkedBlockingQueue<>();
    private Object pauseLock = new Object();
    private boolean paused = false;

    @Override
    public void run() {
        while (true) {
            if (!runnables.isEmpty()) {
                try {
                    runnables.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    synchronized (pauseLock) {
                        paused = true;
                        pauseLock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public BlockingQueue<Runnable> getRunnables() {
        return runnables;
    }

    public boolean isPaused() {
        return paused;
    }

    public void resumeThread() {
        if (isPaused()) {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notify();
            }
        }
    }
}
