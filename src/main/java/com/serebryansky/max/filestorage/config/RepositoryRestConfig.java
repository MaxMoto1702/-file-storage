package com.serebryansky.max.filestorage.config;

import com.serebryansky.max.filestorage.domain.Content;
import com.serebryansky.max.filestorage.validator.ContentBeforeSaveValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(RepositoryRestConfig.class);

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Content.class);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator("beforeSave", new ContentBeforeSaveValidator());
    }
}
