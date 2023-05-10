package com.xingchi.unique.provider.impl;

import com.xingchi.unique.common.IDProviderType;
import com.xingchi.unique.provider.IDProvider;

import java.util.UUID;

/**
 * @author xingchi
 * @date 2023/5/3 10:37
 * @modified xingchi
 */
public class UUIDProvider implements IDProvider<String> {

    @Override
    public String nextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public boolean supports(IDProviderType type) {
        return IDProviderType.UUID.equals(type);
    }

}
