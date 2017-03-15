package com.mobilepark.doit5.elcg.service;

/**
 * Created by kodaji on 15. 9. 16.
 */
public interface JAXBMarshallerService {

    abstract public String marshal(Object object);
    abstract public Object unmarshal(String xml, Object object);
}
