package com.example.base.global.identifier;

import java.io.Serializable;

public interface Identifier {
    public String getIdAsString();
    public byte[] getIdAsByte();
    public default int getIdAsInt(){
        return Integer.parseInt(this.getIdAsString());
    }
}
