package io.sphere.sdk.client;

import play.libs.F;

import java.io.Closeable;

public interface PlayJavaSphereClient extends Closeable {

    <T> F.Promise<T> execute(final SphereRequest<T> sphereRequest);

    void close();

    public static PlayJavaSphereClient of(final SphereClient underlying) {
        return PlayJavaSphereClientImpl.of(underlying);
    }
}
