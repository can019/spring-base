package com.example.base.performance.database.pk.resource.strategy;

import com.fasterxml.uuid.Generators;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;


public class UUIDv1 implements IdentifierGenerator, Configurable {
    private String method;
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        return Generators.timeBasedGenerator().generate();
    }
}