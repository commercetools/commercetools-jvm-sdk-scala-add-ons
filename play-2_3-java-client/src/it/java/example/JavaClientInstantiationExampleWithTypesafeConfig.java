package example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientImpl;

public class SphereClientInstantiationExampleWithTypesafeConfig {
    public void instantiate() throws Exception {
        final Config config = ConfigFactory.load();
        final SphereClient client = new SphereClientImpl(config);
    }
}
