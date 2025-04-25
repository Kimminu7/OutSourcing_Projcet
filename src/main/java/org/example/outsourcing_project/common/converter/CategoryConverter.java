package org.example.outsourcing_project.common.converter;

import org.example.outsourcing_project.common.enums.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<String, Category> {
    @Override
    public Category convert(String source) {
        return Category.from(source);
    }
}