package com.drfa.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class ReconciliationTypeValidatorTest {

    @Test
    public void testIfNothingEnterByUser() throws Exception{
        ReconciliationTypeValidator reconciliationTypeValidator = new ReconciliationTypeValidator();
        assertFalse(reconciliationTypeValidator.validate(""));
    }

    @Test
    public void testIfNullEnterByUser() throws Exception{
        ReconciliationTypeValidator reconciliationTypeValidator = new ReconciliationTypeValidator();
        assertFalse(reconciliationTypeValidator.validate(null));
    }

    @Test
    public void testIfNotExistingValueEnterByUser() throws Exception{
        ReconciliationTypeValidator reconciliationTypeValidator = new ReconciliationTypeValidator();
        assertFalse(reconciliationTypeValidator.validate("NOT-EXIST"));
    }

    @Test
    public void testIfExistingValueEnterByUser() throws Exception{
        ReconciliationTypeValidator reconciliationTypeValidator = new ReconciliationTypeValidator();
        assertTrue(reconciliationTypeValidator.validate("FILE"));
    }
}
