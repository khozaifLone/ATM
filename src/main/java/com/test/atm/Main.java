package com.test.atm;

import java.util.UUID;

/**
 * Driver for te ATM implementation for testing ATM implementation
 */
public class Main {
    public static void main(String[] args) throws InterruptedException { // In application, such exceptions can be handled by Global handlers and makes our operations transactional
        //Initialize ATM with cash
        Atm atm = new Atm();
        AtmOperation atmOperation = new AtmOperation(atm);

        //withdraw cash
        Thread thread1 = new Thread(() -> atmOperation.withDrawCash(5200, UUID.randomUUID().toString()));
        Thread thread2 = new Thread(() -> atmOperation.withDrawCash(12000, UUID.randomUUID().toString()));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("ATM Balance: " + atm.getAtmBalance());
    }
}