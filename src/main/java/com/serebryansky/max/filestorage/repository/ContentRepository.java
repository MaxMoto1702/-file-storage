package com.serebryansky.max.filestorage.repository;

import com.serebryansky.max.filestorage.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
