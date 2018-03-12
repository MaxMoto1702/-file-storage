package com.serebryansky.max.filestorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        Assert.notNull(contentService, "Content service is NULL");
        this.contentService = contentService;
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Content>> list() {
        return ok(contentService.getAll());
    }

    @GetMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Content> get(@PathVariable Long id) {
        if (!contentService.exists(id)) return notFound().build();
        return ok(contentService.get(id));
    }

    @GetMapping(path = "{id}/stream", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getStream(@PathVariable Long id) {
        if (!contentService.exists(id)) return notFound().build();
        return ok(contentService.getBytes(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Content> post(@RequestBody Content content) {
        return ok(contentService.save(content));
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Content> put(@PathVariable Long id, @RequestBody Content content) {
        if (!contentService.exists(id)) return notFound().build();
        return ok(contentService.update(id, content));
    }

    @PutMapping(path = "{id}/stream", consumes = APPLICATION_OCTET_STREAM_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity putStream(@PathVariable Long id, @RequestBody byte[] bytes) {
        if (!contentService.exists(id)) return notFound().build();
        return ok(contentService.updateBytes(id, bytes));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!contentService.exists(id)) return notFound().build();
        contentService.delete(id);
        return noContent().build();
    }
}
