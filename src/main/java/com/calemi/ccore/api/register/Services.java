package com.calemi.ccore.api.register;

import java.util.ServiceLoader;

public class Services {

    public static final IRegistryFactory REGISTRY_FACTORY = load(IRegistryFactory.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}