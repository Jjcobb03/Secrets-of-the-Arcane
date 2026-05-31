package net.jjcobb03.secretsofthearcane.component;

import com.mojang.serialization.Codec;

import java.util.Map;

public record EssentiaContainerData(Map<String, Integer> contents) {

    public static final EssentiaContainerData EMPTY =
            new EssentiaContainerData(Map.of());

    public static final Codec<EssentiaContainerData> CODEC =
            Codec.unboundedMap(
                    Codec.STRING,
                    Codec.INT
            ).xmap(
                    EssentiaContainerData::new,
                    EssentiaContainerData::contents
            );

}
