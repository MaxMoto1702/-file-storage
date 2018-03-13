package com.serebryansky.max.filestorage.service;

public interface ContentDataService {
    Long save(byte[] bytes);
    byte[] get(Long id);
}
