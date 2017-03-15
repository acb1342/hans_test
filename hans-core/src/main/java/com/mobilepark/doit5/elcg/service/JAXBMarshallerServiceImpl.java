package com.mobilepark.doit5.elcg.service;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.mobilepark.doit5.common.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by kodaji on 15. 9. 16.
 */
@Service
public class JAXBMarshallerServiceImpl implements JAXBMarshallerService {
    private final static Logger LOG = LoggerFactory.getLogger(JAXBMarshallerServiceImpl.class);

    @Override
    public String marshal(Object object) {

        if (object == null) return null;

        StringWriter writer = null;
        String xml = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            writer = new StringWriter();
            marshaller.marshal(object, writer);
            xml = writer.toString();
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            IOUtil.close(writer);
        }
        return xml;
    }

    @Override
    public Object unmarshal(String xml, Object object)
    {
        if (xml == null) return null;

        StringReader reader = null;
        Object retObj = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            reader = new StringReader(xml);
            retObj = unmarshaller.unmarshal(reader);
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            IOUtil.close(reader);
        }
        return retObj;
    }
}
