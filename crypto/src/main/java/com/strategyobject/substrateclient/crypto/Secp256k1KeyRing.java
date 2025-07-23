package com.strategyobject.substrateclient.crypto;

import com.strategyobject.substrateclient.common.types.Bytes;
import com.strategyobject.substrateclient.crypto.secp256k1.Secp256k1NativeCryptoProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;

public class Secp256k1KeyRing {
    @Getter
    private final SecretKey secretKey;
    @Getter
    private final PublicKey publicKey;

    private final CryptoProvider cryptoProvider = new Secp256k1NativeCryptoProvider();

    protected Secp256k1KeyRing(SecretKey secretKey, PublicKey publicKey) {
        this.secretKey = secretKey;
        this.publicKey = publicKey;
    }

    protected Secp256k1KeyRing(Seed seed) {
        val keyPair = cryptoProvider.createPairFromSeed(seed);
        this.secretKey = keyPair.asSecretKey();
        this.publicKey = keyPair.asPublicKey();
    }

    public static KeyRing fromKeyPair(@NonNull KeyPair keyPair) {
        return new KeyRing(keyPair.asSecretKey(), keyPair.asPublicKey());
    }

    public static KeyRing fromSeed(@NonNull Seed seed) {
        return new KeyRing(seed);
    }

    public SignatureData sign(@NonNull Bytes message) {
        return cryptoProvider.sign(publicKey, secretKey, message);
    }

    public boolean verifyOwn(@NonNull SignatureData signature, @NonNull Bytes message) {
        return cryptoProvider.verify(signature, message, publicKey);
    }

    public boolean verify(@NonNull SignatureData signature, @NonNull Bytes message, @NonNull PublicKey publicKey) {
        return cryptoProvider.verify(signature, message, publicKey);
    }
}
