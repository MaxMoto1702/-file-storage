package com.serebryansky.max.filestorage.controller;

import com.serebryansky.max.filestorage.domain.Content;
import com.serebryansky.max.filestorage.repository.ContentRepository;
import com.serebryansky.max.filestorage.service.ContentDataService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RepositoryRestController
@RequestMapping("contents")
//@RestController
//@RequestMapping("api/contents")
public class ContentController {
    private static final Logger log = LoggerFactory.getLogger(ContentController.class);

    private final ContentRepository contentRepository;
    private final ContentDataService contentDataService;

    @Autowired
    public ContentController(ContentRepository contentRepository, ContentDataService contentDataService) {
        this.contentRepository = contentRepository;
        this.contentDataService = contentDataService;
    }

//    @GetMapping(path = "{id}/stream", produces = APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<byte[]> getStream(@PathVariable Long id) {
//        log.debug("Get content stream by ID: {}", id);
//        if (!contentRepository.exists(id)) return notFound().build();
//        Content content = contentRepository.findOne(id);
//        return ok(contentDataService.get(content.getContentDataId()));
//    }
//
//    @PutMapping(path = "{id}/stream", consumes = APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity putStream(@PathVariable Long id, @RequestBody byte[] bytes) {
//        log.debug("Update content stream by ID: {}, data size: {}", id, bytes.length);
//        if (!contentRepository.exists(id)) return notFound().build();
//        Content content = contentRepository.findOne(id);
//        content.setSize(bytes.length);
//        Long contentDataId = contentDataService.save(bytes);
//        content.setContentDataId(contentDataId);
//        return ok(contentRepository.save(content));
//    }

    // HACK
    @GetMapping(path = "{id}/stream")
    public void getStream(@PathVariable Long id, HttpServletResponse response) throws IOException {
        log.debug("Get content stream by ID: {}", id);
        IOUtils.write(contentDataService.get(contentRepository.findOne(id).getContentDataId()), response.getOutputStream());
    }

    // HACK
    @PutMapping(path = "{id}/stream")
    public void putStream(@PathVariable Long id, HttpServletRequest request) throws IOException {
        log.debug("Update content stream by ID: {}", id);
        byte[] bytes = IOUtils.toByteArray(request.getInputStream());
        Content content = contentRepository.findOne(id);
        content.setSize(bytes.length);
        Long contentDataId = contentDataService.save(bytes);
        content.setContentDataId(contentDataId);
        contentRepository.save(content);
    }
}
