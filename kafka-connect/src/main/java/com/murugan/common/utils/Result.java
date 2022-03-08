package com.murugan.common.utils;

//public record Result<T, E extends Throwable>(T ok, E err) {
//    public boolean isOK() {
//        return ok != null;
//    }
//}

public class Result<T, E extends Throwable> {
    private final T ok;
    private final E err;

    public Result(T ok, E err) {
        this.ok = ok;
        this.err = err;
    }

    public boolean isOK() {
        return ok != null;
    }

    public T result() {
        return ok;
    }
}
