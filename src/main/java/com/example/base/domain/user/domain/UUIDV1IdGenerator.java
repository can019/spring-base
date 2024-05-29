package com.example.base.domain.user.domain;

import com.example.base.global.util.generator.UUID;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;

public class UUIDV1IdGenerator implements IdentifierGenerator, Configurable {
    private String method;
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
     return UUID.generateSequentialUUIDV1WithoutHyphenAsByte();
    }
}
