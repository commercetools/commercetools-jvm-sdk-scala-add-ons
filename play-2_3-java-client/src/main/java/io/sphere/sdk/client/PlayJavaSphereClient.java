package io.sphere.sdk.client;

import play.libs.F;

import java.io.Closeable;

public interface PlayJavaSphereClient extends Closeable {

    <T> F.Promise<T> execute(final SphereRequest<T> SphereRequest);

    void close();
}
