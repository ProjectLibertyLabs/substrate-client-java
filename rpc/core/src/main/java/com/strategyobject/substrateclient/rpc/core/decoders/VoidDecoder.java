package com.strategyobject.substrateclient.rpc.core.decoders;

import com.google.common.base.Preconditions;
import com.strategyobject.substrateclient.rpc.core.DecoderPair;
import com.strategyobject.substrateclient.rpc.core.RpcDecoder;

public class VoidDecoder implements RpcDecoder<Void> {
    @Override
    public Void decode(Object value, DecoderPair<?>... decoders) {
        Preconditions.checkArgument(decoders == null || decoders.length == 0);

        return null;
    }
}
