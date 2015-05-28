package io.sphere.sdk.client;

import play.libs.F;

public interface PlayJavaSphereClient extends AutoCloseable {

    <T> F.Promise<T> execute(final SphereRequest<T> sphereRequest);

    void close();

    static PlayJavaSphereClient of(final SphereClient underlying) {
        return PlayJavaSphereClientImpl.of(underlying);
    }
}
