package com.serebryansky.max.filestorage;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Override
    public List<Content> getAll() {
        return Arrays.asList(new Content(), new Content());
    }

    @Override
    public Content get(Long id) {
        return new Content();
    }

    @Override
    public boolean exists(Long id) {
        return true;
    }

    @Override
    public byte[] getBytes(Long id) {
        return new byte[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    @Override
    public Content save(Content content) {
        content.setId(1L);
        content.setSize(0);
        return content;
    }

    @Override
    public Content update(Long id, Content content) {
        content.setId(id);
        content.setSize(0);
        return content;
    }

    @Override
    public Content updateBytes(Long id, byte[] bytes) {
        Content content = new Content();
        content.setId(id);
        content.setName("file.xml");
        content.setMimeType("application/xml");
        content.setSize(bytes.length);
        return content;
    }

    @Override
    public Content delete(Long id) {
        return new Content();
    }
}
