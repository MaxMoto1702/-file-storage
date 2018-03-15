package com.serebryansky.max.filestorage;

import com.serebryansky.max.filestorage.service.ContentDataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class ContentDataServiceTestImpl implements ContentDataService {
    @Override
    public Long save(byte[] bytes) {
        return System.currentTimeMillis();
    }

    @Override
    public byte[] get(Long id) {
        return "test".getBytes();
    }
}
