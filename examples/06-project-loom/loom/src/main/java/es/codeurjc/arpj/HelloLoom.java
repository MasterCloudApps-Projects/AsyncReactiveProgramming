package es.codeurjc.arpj;

public class HelloLoom {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello @ " + Thread.currentThread().getName());

        final Thread myVt = Thread.startVirtualThread(
                () -> System.out.println("Hello Loom! @ "
                        + Thread.currentThread().getName()));
        myVt.join();

        System.out.println("Goodbye @ " + Thread.currentThread().getName());
    }
}
