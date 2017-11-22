package com.vb.appmvp.net;

import com.vb.appmvp.VbConf;
import com.vb.appmvp.kit.Kits;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C) 2015 - 2017 MICROSCENE Inc., All Rights Reserved.
 *
 * @author: seven@vb.com.cn
 * @date: 2017-08-15
 */
public class VbApi {
    private static NetProvider sProvider=null;
    private Map<String, NetProvider> providerMap = new HashMap<>();
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String, OkHttpClient> clientMap = new HashMap<>();

    public static final long connectTimeoutMills = VbConf.CONNECT_TIMEOUT;
    public static final long readTimeoutMills = VbConf.READ_TIMEOUT;

    private static VbApi instance;

    private VbApi()
    {

    }
    public static VbApi getInstance()
    {
        if(instance==null)
        {
            synchronized (VbApi.class)
            {
                if(instance==null)
                {
                    instance=new VbApi();
                }
            }
        }
        return instance;
    }
    public static <S> S get(String baseUrl, Class<S> service) {
        return getInstance().getRetrofit(baseUrl, true).create(service);
    }
    public static void registerProvider(NetProvider provider) {
        VbApi.sProvider = provider;
    }

    public static void registerProvider(String baseUrl, NetProvider provider) {
        getInstance().providerMap.put(baseUrl, provider);
    }

    public Retrofit getRetrofit(String baseUrl,boolean useRx)
    {
        return getRetrofit(baseUrl,null,useRx);
    }
    private void checkProvider(NetProvider provider) {
        if (provider == null) {
            throw new IllegalStateException("must register provider first");
        }
    }
    public Retrofit getRetrofit(String baseUrl,NetProvider provider,boolean useRx)
    {
        if(Kits.Empty.check(baseUrl))
        {
            throw  new IllegalStateException("baseUrl can not be null");
        }
        if(retrofitMap.get(baseUrl)!=null)
        {
            return retrofitMap.get(baseUrl);
        }
        if(provider==null)
        {
            provider=providerMap.get(baseUrl);
            if(provider==null)
            {
                provider=sProvider;
            }

        }
        checkProvider(provider);
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl,provider))
                .addConverterFactory(GsonConverterFactory.create());
        if(useRx)
        {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        Retrofit retrofit=builder.build();

        retrofitMap.put(baseUrl, retrofit);
        providerMap.put(baseUrl, provider);

        return retrofit;


    }
    private OkHttpClient getClient(String baseUrl,NetProvider provider)
    {

        if(Kits.Empty.check(baseUrl))
        {
            throw  new IllegalStateException("baseUrl can not be null");
        }
        if(clientMap.size()>0 && clientMap.get(baseUrl) != null)
        {
            return clientMap.get(baseUrl);
        }
        checkProvider(provider);
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                ? provider.configConnectTimeoutMills()
                : connectTimeoutMills, TimeUnit.MILLISECONDS);
        builder.readTimeout(provider.configReadTimeoutMills() != 0
                ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);
        CookieJar cookieJar=provider.configCookie();
        if(cookieJar!=null)
        {
            builder.cookieJar(cookieJar);
        }
        provider.configHttps(builder);
        RequestCallback callback=provider.configHandler();
        if(callback!=null)
        {
            builder.addInterceptor(new VbInterceptor(callback));
        }
        Cache cache=provider.addCache();
        if(cache!=null)
        {
            builder.cache(cache);
        }

        List<Interceptor> interceptors = provider.configInterceptors();
        if (!Kits.Empty.check(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        OkHttpClient client = builder.build();

        clientMap.put(baseUrl, client);
        providerMap.put(baseUrl, provider);

        return client;
    }
    public static NetProvider getCommonProvider() {
        return sProvider;
    }

    public Map<String, Retrofit> getRetrofitMap() {
        return retrofitMap;
    }

    public Map<String, OkHttpClient> getClientMap() {
        return clientMap;
    }

    public static void clearCache() {
        getInstance().retrofitMap.clear();
        getInstance().clientMap.clear();
    }
    /**
     * 线程切换
     *
     * @return
     */
    public static <T extends IModel> FlowableTransformer<T, T> getScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    /**
     * 异常处理变换
     *
     * @return
     */
    public static <T extends IModel> FlowableTransformer<T, T> getApiTransformer() {

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.flatMap(new Function<T, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(T model) throws Exception {

                        if (model == null || model.isNull()) {
                            return Flowable.error(new NetError(model.getErrorMsg(), NetError.NoDataError));
                        }else if(model.isInsideError())
                        {
                            return Flowable.error(new NetError(model.getErrorMsg(),NetError.InsideError));
                        }

                        else if (model.isAuthError()) {
                            return Flowable.error(new NetError(model.getErrorMsg(), NetError.AuthError));
                        } else if (model.isBusinessError()) {
                            return Flowable.error(new NetError(model.getErrorMsg(), NetError.BusinessError));
                        } else {
                            return Flowable.just(model);
                        }
                    }
                });
            }
        };
    }
    /**
     * 转换封装
     */
    public static <T extends IModel> FlowableTransformer<T, T> getCommonTransformer()
    {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {

                return upstream
                        .compose(VbApi.<T>getApiTransformer())
                        /**一秒内多次请求无效**/
                      //  .throttleFirst(1,TimeUnit.SECONDS)
                        .compose(VbApi.<T>getScheduler());

            }
        };
    }
}
