package com.strategyobject.substrateclient.rpc.api;

import com.strategyobject.substrateclient.crypto.ss58.SS58Codec;
import com.strategyobject.substrateclient.rpc.DecoderPair;
import com.strategyobject.substrateclient.rpc.RpcDecoder;
import com.strategyobject.substrateclient.rpc.annotation.AutoRegister;
import com.strategyobject.substrateclient.transport.RpcObject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AutoRegister(types = AccountId.class)
public class AccountIdDecoder implements RpcDecoder<AccountId> {

    @Override
    public AccountId decode(RpcObject value, DecoderPair<?>... decoders) {
        if (value.isNull()) { return null; }
        if (decoders != null && decoders.length > 0) throw new IllegalArgumentException();

        return AccountId.fromBytes(SS58Codec.decode(value.asString()).getAddress());
    }

}
