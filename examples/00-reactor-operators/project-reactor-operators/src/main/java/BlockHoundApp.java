import reactor.blockhound.BlockHound;
import reactor.core.scheduler.Scheduler;

import static reactor.core.scheduler.Schedulers.newParallel;

public class BlockHoundApp {

    public static void main(String[] args) {

        BlockHound.install();
        new MyNefariousOperation().evil();
    }
}

class MyNefariousOperation {

    public void evil() {

        System.out.println("Hello!!! I want to sleep!!!");

        final Scheduler scheduler = newParallel("MyParallel");

        scheduler.schedule(() -> {
            try {
                Thread.sleep(666);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
