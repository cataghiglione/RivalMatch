
import spark.Spark;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class RivalMatchService {

    private final Routes routes = new Routes();

    public void start() {
        startWebServer();
    }

    public void stop() {
        stopWebServer();
    }

    private void startWebServer() {
        staticFiles.location("public");
        port(4326);
        final RivalMatchSystem system = RivalMatchSystem.create("rmatch");
        routes.create(system);
    }

    private void stopWebServer() {
        Spark.stop();
    }

}
