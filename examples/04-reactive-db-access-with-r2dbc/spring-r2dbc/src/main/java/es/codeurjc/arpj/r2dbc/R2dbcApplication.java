package es.codeurjc.arpj.r2dbc;

import es.codeurjc.arpj.r2dbc.infrastructure.persistence.AstronautEntity;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Row;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.BiFunction;

@SpringBootApplication
public class R2dbcApplication {

    private static final String SEPARATOR = "-------------------------------------------------------------------------";

    private static final ConnectionFactoryOptions options;

    static {
        options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 5432)
                .option(ConnectionFactoryOptions.DATABASE, "arpj_example_04")
                .option(ConnectionFactoryOptions.DRIVER, "postgres")
                .option(ConnectionFactoryOptions.USER, "arpj04")
                .option(ConnectionFactoryOptions.PASSWORD, "arpj04")
                .build();
    }

    public static void main(String[] args) {
        BlockHound.install();
        SpringApplication.run(R2dbcApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void basicPlainR2dbcUsage() throws InterruptedException {

        System.out.println(SEPARATOR);
        System.out.println("Basic plain R2DBC usage (ApplicationReadyEvent)");
        System.out.println(SEPARATOR);

        ConnectionFactory connectionFactory = ConnectionFactories.get(options);

        Flux.from(connectionFactory.create())
                .flatMap(
                        conn -> conn.createStatement("SELECT * FROM astronauts WHERE space_flights > $1")
                                .bind("$1", 5)
                                .execute())
                .flatMap(
                        r -> r.map((rw, rwm) -> rw.get("name", String.class)
                                + " - " + rw.get("space_flights", Integer.class)))
                .delayElements(Duration.ofMillis(250))
                .doOnNext(System.out::println)
                .doOnComplete(() -> System.out.println(SEPARATOR))
                .subscribe();

        Thread.sleep(3000);
        r2dbcWithDatabaseClient();
    }

    private void r2dbcWithDatabaseClient() {

        System.out.println(SEPARATOR);
        System.out.println("R2DBC with database client");
        System.out.println(SEPARATOR);

        ConnectionFactory connectionFactory = ConnectionFactories.get(options);

        DatabaseClient  client = DatabaseClient.create(connectionFactory);
        AstronautMapper mapper = new AstronautMapper();

        client
                .sql("SELECT * FROM astronauts WHERE status = $1")
                .bind("$1", "D")
                .map(mapper::apply)
                .all()
                .take(15)
                .delayElements(Duration.ofMillis(100))
                .doOnNext(a -> System.out.println(a.simpleToString()))
                .doOnComplete(() -> System.out.println(SEPARATOR))
                .subscribe();
    }
}

@SuppressWarnings("ConstantConditions")
class AstronautMapper implements BiFunction<Row, Object, AstronautEntity> {

    @Override

    public AstronautEntity apply(final Row r, final Object o) {

        final var astronaut = new AstronautEntity();

        astronaut.setId(r.get("id", Long.class));
        astronaut.setName(r.get("name", String.class));
        astronaut.setGender(r.get("gender", String.class));
        astronaut.setStatus(r.get("status", String.class));
        astronaut.setMissions(r.get("missions", String.class));
        astronaut.setSpaceWalks(r.get("space_walks", Integer.class));
        astronaut.setSpaceFlights(r.get("space_flights", Integer.class));

        return astronaut;
    }
}