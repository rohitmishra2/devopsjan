package com.wip;

public class MainClass {

    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        MainClass mc = new MainClass();

        System.out.println(mc.add(10, 20));
        System.out.println("Java For Docker!!!! . THis code is pulled from WIP GitHub Repository");
        System.out.println("This is the updated code after adding Jenkinsfile to the repository");

        
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("App is running... heartbeat");
                    Thread.sleep(10_000); // 10 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // restore interrupt flag
                }
            }
            System.out.println("Worker thread stopped.");
        }, "heartbeat-worker");

       
        worker.setDaemon(false);
        worker.start();

       
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown signal received. Stopping worker...");
            worker.interrupt();
            try {
                worker.join(3000); // wait up to 3 seconds
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Shutdown complete.");
        }, "shutdown-hook"));

        
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
