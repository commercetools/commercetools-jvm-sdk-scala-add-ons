package io.sphere.sdk.util.functional;

public final class ScalaFunctionConversions {
    private ScalaFunctionConversions() {
    }

    public static <I, O> java.util.function.Function<I, O> s2j(final scala.Function1<I, O> input) {
        return i -> input.apply(i);
    }
}
