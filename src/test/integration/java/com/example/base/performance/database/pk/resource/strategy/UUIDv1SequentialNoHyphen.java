package com.example.base.performance.database.pk.resource.strategy;

import com.example.base.global.util.generator.UUID;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;

public class UUIDv1SequentialNoHyphen implements IdentifierGenerator, Configurable {
    private String method;
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        return UUID.generateSequentialUUIDV1WithoutHyphenAsByte();
    }
}