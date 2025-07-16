package com.strategyobject.substrateclient.crypto;

import com.strategyobject.substrateclient.common.types.FixedBytes;
import com.strategyobject.substrateclient.common.types.Size;
import lombok.NonNull;

public class SignatureData extends FixedBytes<Size> {

    protected SignatureData(byte @NonNull [] bytes, Size size) {
        super(bytes, size);
    }

    public static SignatureData fromBytes(byte @NonNull [] data) {
        if (data.length == 64) {
            return new SignatureData(data, Size.of64);
        }
        // NOTE(Julian, 2025-07-16): ECDSA (Secp256k1) signatures are 65 bytes
        if (data.length == 65) {
            return new SignatureData(data, Size.of65);
        }

        throw new IllegalArgumentException("Unsupported data size: " + data.length);
    }

}
