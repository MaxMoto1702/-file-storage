package com.serebryansky.max.filestorage.validator;

import com.serebryansky.max.filestorage.domain.Content;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContentBeforeSaveValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Content.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
