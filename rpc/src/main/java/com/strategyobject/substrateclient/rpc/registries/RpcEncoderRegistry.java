package com.strategyobject.substrateclient.rpc.registries;

import com.strategyobject.substrateclient.common.reflection.ClassUtils;
import com.strategyobject.substrateclient.common.reflection.Scanner;
import com.strategyobject.substrateclient.common.types.Array;
import com.strategyobject.substrateclient.common.types.Unit;
import com.strategyobject.substrateclient.rpc.RpcDispatch;
import com.strategyobject.substrateclient.rpc.RpcEncoder;
import com.strategyobject.substrateclient.rpc.annotation.AutoRegister;
import com.strategyobject.substrateclient.rpc.context.RpcEncoderContext;
import com.strategyobject.substrateclient.rpc.context.RpcEncoderContextFactory;
import com.strategyobject.substrateclient.rpc.encoders.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RpcEncoderRegistry {
    private final Map<Class<?>, RpcEncoder<?>> encoders;

    public RpcEncoderRegistry() {
        encoders = new ConcurrentHashMap<>(128);
        register(new PlainEncoder<>(),
                Void.class, void.class, String.class, Boolean.class, boolean.class, Byte.class, byte.class,
                Double.class, double.class, Float.class, float.class, BigInteger.class, Integer.class, int.class,
                Long.class, long.class, Short.class, short.class);
        register(new UnitEncoder(), Unit.class);
        register(new ListEncoder(), List.class);
        register(new MapEncoder(), Map.class);
        register(new ArrayEncoder(), Array.class);
        register(new DispatchingEncoder<>(this), RpcDispatch.class);
    }

    public void registerAnnotatedFrom(RpcEncoderContextFactory rpcEncoderContextFactory, String... prefixes) {
        Scanner.forPrefixes(prefixes)
                .getSubTypesOf(RpcEncoder.class).forEach(encoder -> {
                    val autoRegister = encoder.getAnnotation(AutoRegister.class);
                    if (autoRegister == null) {
                        return;
                    }

                    try {
                        val types = autoRegister.types();
                        log.info("Auto register encoder {} for types: {}", encoder, types);

                        RpcEncoder<?> rpcEncoder;
                        if (ClassUtils.hasDefaultConstructor(encoder)) {
                            rpcEncoder = encoder.newInstance();
                        } else {
                            val ctor = encoder.getConstructor(RpcEncoderContext.class);
                            rpcEncoder = ctor.newInstance(rpcEncoderContextFactory.create());
                        }

                        register(rpcEncoder, types);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        log.error("Auto registration error", e);
                    }
                });
    }

    public <T> void register(@NonNull RpcEncoder<T> encoder, @NonNull Class<?>... clazz) {
        for (val type : clazz) {
            encoders.put(type, encoder);
        }
    }

    public RpcEncoder<?> resolve(@NonNull Class<?> clazz) {
        return encoders.get(clazz);
    }
}
