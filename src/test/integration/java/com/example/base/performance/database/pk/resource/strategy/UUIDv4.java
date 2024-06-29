package com.example.base.performance.database.pk.resource.strategy;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

public class UUIDv4 implements IdentifierGenerator, Configurable {
    private String method;
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        return UUID.randomUUID();
    }
}
