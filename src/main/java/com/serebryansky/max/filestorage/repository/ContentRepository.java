package com.serebryansky.max.filestorage.repository;

import com.serebryansky.max.filestorage.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
