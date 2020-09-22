package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class Convert {
    private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    public static <T> T convert(Object source, Class<T> destinationClass) {
        return MAPPER.map(source, destinationClass);
    }
}
