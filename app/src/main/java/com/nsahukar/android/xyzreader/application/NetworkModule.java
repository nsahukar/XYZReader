package com.nsahukar.android.xyzreader.application;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.nsahukar.android.base.injection.scopes.ApplicationScope;
import com.nsahukar.android.base.network.NetworkUnavailableException;

import java.io.IOException;
import java.util.Set;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nikhil on 08/12/17.
 */

@Module
class NetworkModule {
    private static final String BASE_URL = "https://go.udacity.com/";

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Provides
    @ApplicationScope
    static Retrofit provideApi(@Named("BASE_URL") String baseUrl,
                               Gson gson,
                               OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Named("BASE_URL")
    static String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    @ApplicationScope
    static Gson provideGson(Set<TypeAdapterFactory> typeAdapters) {
        final GsonBuilder builder = new GsonBuilder();
        for (TypeAdapterFactory factory : typeAdapters) {
            builder.registerTypeAdapterFactory(factory);
        }
        return builder.create();
    }

    @Provides
    @ApplicationScope
    static OkHttpClient provideOkHttpClient(XYZReaderApplication application) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // intercepting request to check network availability
                // If not available, throw NetworkUnavailableException
                if (!isNetworkAvailable(application.getApplicationContext())) {
                    throw new NetworkUnavailableException();
                }
                // if available, proceed with the request
                return chain.proceed(chain.request());
            }
        });
        return httpClientBuilder.build();
    }
}
