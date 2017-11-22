package com.vb.appmvp.cookie;


import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 管理cookies
 */

public class CookieManager implements CookieJar {

    private static PersistentCookieStore cookieStore;

    public CookieManager(Context context) {
        if (cookieStore == null ) {
            cookieStore = new PersistentCookieStore(context);
        }

    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }

    public List<Cookie> getCookies(){
        return cookieStore.getCookies();
    }
}
