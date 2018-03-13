package com.serebryansky.max.filestorage.service;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.*;

@Service
public class ContentDataServiceImpl implements ContentDataService {
    private static final Logger log = LoggerFactory.getLogger(ContentDataServiceImpl.class);

    private static final File DATA_FOLDER = new File("/data");

    @Override
    public Long save(byte[] bytes) {
        long id = System.currentTimeMillis();
        try (FileOutputStream stream = new FileOutputStream(new File(DATA_FOLDER, String.valueOf(id)))) {
            IOUtils.write(bytes, stream);
        } catch (IOException e) {
            log.error("Error in write data to disk", e);
            throw new RuntimeException("Error in write data to disk", e);
        }
        return id;
    }

    @Override
    public byte[] get(Long id) {
        Assert.notNull(id, "Content data ID is NULL");
        try (InputStream stream = new FileInputStream(new File(DATA_FOLDER, id.toString()))) {
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            log.error("Error in read data from disk", e);
            throw new RuntimeException("Error in read data from disk", e);
        }
    }
}
