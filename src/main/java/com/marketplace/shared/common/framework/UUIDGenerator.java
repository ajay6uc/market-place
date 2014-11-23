package com.marketplace.shared.common.framework;

import java.util.UUID;

import com.marketplace.shared.common.core.NBUID;


public final class UUIDGenerator {
    
    //generates random UUID (e.g. "3f91f07a-ad6c-4705-b981-984b8e2907f7")
    public static synchronized NBUID generate() {
        return new NBUID(UUID.randomUUID().toString());
    }
    
    private UUIDGenerator() {
    }
}
