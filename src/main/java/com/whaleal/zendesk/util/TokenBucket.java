package com.whaleal.zendesk.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 实现限速 ，此项暂未投入使用
 */
public class TokenBucket {
    private final BlockingQueue<Object> bucket;
    private final int capacity;
    private final int rate;

    public TokenBucket(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.bucket = new ArrayBlockingQueue<>(capacity);

        Thread tokenGenerator = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 / rate);
                    bucket.put(new Object());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tokenGenerator.start();
    }

    public void acquire() {
        try {
            bucket.take();
            System.out.println("Request processed");
        } catch (InterruptedException e) {
            System.out.println("Request rejected");
        }
    }

    public static void main(String[] args) {
        TokenBucket tokenBucket = new TokenBucket(10, 1); // 桶容量为10，每秒生成1个令牌
        for (int i = 0; i < 20; i++) {
            tokenBucket.acquire();
        }
    }
}
