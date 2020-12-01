import com.github.javafaker.Faker;
import reactor.core.publisher.Flux;
import reactor.tools.agent.ReactorDebugAgent;

public class ReactorDebugAgentApp {

    public static void main(String[] args) {
        ReactorDebugAgent.init();
        new MyOperation().myOperation();
    }
}

class MyOperation {

    public void myOperation() {

        final Faker faker = new Faker();

        Flux.range(0, 10)
                .map(x -> faker.twinPeaks().character())
                .map(x -> x.charAt(10))
                .subscribe();

    }
}
