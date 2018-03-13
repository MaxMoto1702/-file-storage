package com.serebryansky.max.filestorage.service;

import com.serebryansky.max.filestorage.domain.Content;

import java.util.List;

public interface ContentService {
    List<Content> getAll();

    Content get(Long id);

    boolean exists(Long id);

    byte[] getBytes(Long id);

    Content save(Content content);

    Content update(Long id, Content content);

    Content updateBytes(Long id, byte[] bytes);

    void delete(Long id);
}
