package com.strategyobject.substrateclient.rpc.api.primitives;

import com.strategyobject.substrateclient.scale.ScaleType;
import com.strategyobject.substrateclient.scale.annotation.Scale;
import com.strategyobject.substrateclient.scale.annotation.ScaleReader;

@ScaleReader
public class MappedEntityIdentifier {
    private EntityIdentifierKind kind;
    @Scale(ScaleType.U16.class)
    private Integer id;

    public MappedEntityIdentifier() {}

    public MappedEntityIdentifier(EntityIdentifierKind kind, Integer id) {
        this.kind = kind;
        this.id = id;
    }

    public EntityIdentifierKind getKind() {
        return kind;
    }

    public void setKind(EntityIdentifierKind kind) {
        this.kind = kind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
