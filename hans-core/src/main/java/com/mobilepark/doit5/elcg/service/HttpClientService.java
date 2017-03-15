package com.mobilepark.doit5.elcg.service;

import org.apache.http.Header;
import org.apache.http.StatusLine;

/**
 * Created by kodaji on 15. 9. 16.
 */
public interface HttpClientService {

    abstract public StatusLine getStatusLine();
    abstract public Header getHeader(String name);
    abstract public String httpDelete(String url, Header[] headers);
    abstract public String httpGet(String url, Header[] headers);
    abstract public String httpPost(String LOG_PREFIX, String url, Header[] headers, String body);
    abstract public String httpPut(String LOG_PREFIX, String url, Header[] headers, String body);
}
