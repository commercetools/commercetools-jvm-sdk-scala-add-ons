package io.sphere.sdk.client;

import play.libs.F;

import static java.util.Objects.requireNonNull;

public interface PlayJavaSphereClient extends AutoCloseable {

    <T> F.Promise<T> execute(final SphereRequest<T> sphereRequest);

    @Override
    void close();

    static PlayJavaSphereClient of(final SphereClient underlying) {
        final String message = "Underlying client instance should not be null.";
        return PlayJavaSphereClientImpl.of(requireNonNull(underlying, message));
    }
}
