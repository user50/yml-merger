package com.company.main;

import com.company.Util;
import com.company.http.HttpResponseHandler;
import com.company.yml.YmlCatalog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user50 on 21.06.2015.
 */
public class FileDownloadHandler implements HttpResponseHandler<YmlCatalog> {

    String encoding;

    public FileDownloadHandler(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public YmlCatalog handle(CloseableHttpResponse httpResponse) {
        HttpEntity entity = httpResponse.getEntity();

        try {
            return Util.unmarshal(entity.getContent(), YmlCatalog.class, encoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
