package es.codeurjc.arpj;

public class HelloLoom {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello @ " + Thread.currentThread().getName());

        Thread myVt = Thread.builder()
                .virtual()
                .name("my-virtual-thread")
                .task(() -> System.out.println("Hello Loom! @ " + Thread.currentThread().getName()))
                .build();
        myVt.start();

        Thread.sleep(1000);
        System.out.println("Goodbye @ " + Thread.currentThread().getName());
    }
}
