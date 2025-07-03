/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.space;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AuditableSpace is a marker annotation to identify subclasses of {@link BComponentSpace}
 * that are intended to be audited in {@link com.tridium.sys.schema.ComplexSlotMap}.
 * A ComplexSlotMap whose space is a ComponentSpace TYPE or whose space is annotated
 * with this interface will allow audit(...) calls. Others will be ignored and logged.
 *
 * This annotation could be used for other purposes in the future, as long as they do
 * not conflict with its original intended purpose.  If its purpose is extended this
 * comment should be updated to reflect the changes.
 *
 * @author    Robert Staley on 03 Sept 2021
 * @since     Niagara 4.12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AuditableSpace
{
}
