package com.mea.datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Simple test type to verify type registration works.
 */
@NiagaraType
public class BTestType extends BObject {

    /**
     * Required Type field for all BObject subclasses.
     */
    public static final Type TYPE = Sys.loadType(BTestType.class);

    /**
     * Get the Type of this object.
     * @return the Type
     */
    public Type getType() {
        return TYPE;
    }

    /**
     * Default constructor.
     */
    public BTestType() {
    }
}
