package com.xuyiyang.utils;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * Created by xuyiyang on 16-7-13.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Reflects {
    public static <T> Set<Class<? extends T>> scanClasses(String prefix,
                                                          Class<T> klass,
                                                          Class<? extends Annotation> annotation) {
        Set<Class<? extends T>> klasses = Sets.newHashSet();

        ConfigurationBuilder config = new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath())
                .filterInputsBy(new FilterBuilder().includePackage(prefix));

        for (Class<? extends T> clz : new Reflections(config).getSubTypesOf(klass)) {
            log.debug("Scanned class {}", clz.getCanonicalName());

            if (Objects.isNull(annotation) || clz.isAnnotationPresent(annotation))
                klasses.add(clz);
        }
        return klasses;
    }
}
