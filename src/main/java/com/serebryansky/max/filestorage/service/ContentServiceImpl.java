package com.serebryansky.max.filestorage.service;

import com.serebryansky.max.filestorage.domain.Content;
import com.serebryansky.max.filestorage.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final ContentDataService contentDataService;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository, ContentDataService contentDataService) {
        this.contentRepository = contentRepository;
        this.contentDataService = contentDataService;
    }

    @Override
    public List<Content> getAll() {
        return contentRepository.findAll();
    }

    @Override
    public Content get(Long id) {
        return contentRepository.findOne(id);
    }

    @Override
    public boolean exists(Long id) {
        return contentRepository.exists(id);
    }

    @Override
    public byte[] getBytes(Long id) {
        Content content = contentRepository.findOne(id);
        return contentDataService.get(content.getContentDataId());
    }

    @Override
    public Content save(Content content) {
        content.setSize(0);
        return contentRepository.save(content);
    }

    @Override
    public Content update(Long id, Content content) {
        Content contentFromDb = contentRepository.findOne(id);
        if (content.getName() != null) contentFromDb.setName(content.getName());
        if (content.getType() != null) contentFromDb.setType(content.getType());
        return contentRepository.save(contentFromDb);
    }

    @Override
    public Content updateBytes(Long id, byte[] bytes) {
        Content content = contentRepository.findOne(id);
        content.setSize(bytes.length);
        Long contentDataId = contentDataService.save(bytes);
        content.setContentDataId(contentDataId);
        return contentRepository.save(content);
    }

    @Override
    public void delete(Long id) {
        contentRepository.delete(id);
    }
}
