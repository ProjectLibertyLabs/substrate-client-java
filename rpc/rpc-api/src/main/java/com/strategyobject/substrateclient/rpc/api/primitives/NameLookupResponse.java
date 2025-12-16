package com.strategyobject.substrateclient.rpc.api.primitives;

import com.strategyobject.substrateclient.scale.annotation.ScaleReader;

@ScaleReader
public class NameLookupResponse {
    private String name; // Vec<u8> in rust
    private MappedEntityIdentifier entityId;

    public NameLookupResponse() {}

    public NameLookupResponse(String name, MappedEntityIdentifier entityId) {
        this.name = name;
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MappedEntityIdentifier getEntityId() {
        return entityId;
    }

    public void setEntityId(MappedEntityIdentifier entityId) {
        this.entityId = entityId;
    }
}
