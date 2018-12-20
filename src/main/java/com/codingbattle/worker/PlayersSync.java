package com.codingbattle.worker;

import java.util.concurrent.CountDownLatch;

public class PlayersSync implements Runnable {

    private CountDownLatch countDownLatch = new CountDownLatch(2);

    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
