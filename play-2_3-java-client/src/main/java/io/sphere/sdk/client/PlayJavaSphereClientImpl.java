package io.sphere.sdk.client;

import play.libs.F;

class PlayJavaSphereClientImpl implements PlayJavaSphereClient {
    private final ScalaClient scalaClient;

    public PlayJavaSphereClientImpl(final ScalaClient scalaClient) {
        this.scalaClient = scalaClient;
    }

    @Override
    public <T> F.Promise<T> execute(final SphereRequest<T> SphereRequest) {
        return F.Promise.<T>wrap(scalaClient.execute(SphereRequest));
    }

    @Override
    public void close() {
        scalaClient.close();
    }

}
