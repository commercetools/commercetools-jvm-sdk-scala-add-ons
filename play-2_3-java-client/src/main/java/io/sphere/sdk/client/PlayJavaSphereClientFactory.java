package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;

import java.util.function.Function;

public class PlayJavaSphereClientFactory extends Base implements ClientFactory<PlayJavaSphereClient> {

    private PlayJavaSphereClientFactory() {
    }

    @Override
    public PlayJavaSphereClient createClient(SphereClientConfig sphereClientConfig) {
        return new PlayJavaSphereClientImpl(ScalaSphereClientFactory.createClient(sphereClientConfig));
    }

    @Override
    public PlayJavaSphereClient createClient(SphereApiConfig sphereApiConfig, SphereAccessTokenSupplier sphereAccessTokenSupplier) {
        return new PlayJavaSphereClientImpl(ScalaSphereClientFactory.createClient(sphereApiConfig, sphereAccessTokenSupplier));
    }

    @Override
    public PlayJavaSphereClient createHttpTestDouble(Function<HttpRequest, HttpResponse> function) {
        return new PlayJavaSphereClientImpl(ScalaSphereClientFactory.createHttpTestDouble(function));
    }

    @Override
    public PlayJavaSphereClient createObjectTestDouble(Function<HttpRequest, Object> function) {
        return new PlayJavaSphereClientImpl(ScalaSphereClientFactory.createObjectTestDouble(function));
    }

    public static PlayJavaSphereClientFactory of() {
        return new PlayJavaSphereClientFactory();
    }
}
