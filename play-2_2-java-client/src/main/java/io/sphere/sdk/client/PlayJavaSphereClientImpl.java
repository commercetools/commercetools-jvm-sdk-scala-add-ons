package io.sphere.sdk.client;

import play.libs.F;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;

import java.util.concurrent.CompletableFuture;

final class PlayJavaSphereClientImpl implements PlayJavaSphereClient {
    private final SphereClient sphereClient;

    private PlayJavaSphereClientImpl(final SphereClient sphereClient) {
        this.sphereClient = sphereClient;
    }

    @Override
    public <T> F.Promise<T> execute(final SphereRequest<T> sphereRequest) {
        return convert(sphereClient.execute(sphereRequest));
    }

    @Override
    public void close() {
        sphereClient.close();
    }

    private static <T> F.Promise<T> convert(final CompletableFuture<T> future) {
        final Promise<T> scalaPromise = Promise$.MODULE$.apply();
        future.whenComplete((value, throwable) -> {
            if (throwable == null) {
                scalaPromise.success(value);
            } else {
                scalaPromise.failure(throwable);
            }
        });
        return F.Promise.wrap(scalaPromise.future());
    }

    public static PlayJavaSphereClient of(final SphereClient sphereClient) {
        return new PlayJavaSphereClientImpl(sphereClient);
    }
}
