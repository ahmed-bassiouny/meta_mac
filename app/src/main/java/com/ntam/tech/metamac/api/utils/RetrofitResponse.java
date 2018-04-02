package com.ntam.tech.metamac.api.utils;

/**
 * Created by ahmed on 10/10/17.
 */

public interface RetrofitResponse<T> {
    void onSuccess(T t);
    void onFailed(String errorMessage);
}
