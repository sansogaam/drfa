package com.drfa.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class ReportExtensionValidatorTest {

    @Test
    public void testIfNothingEnterByUser() throws Exception{
        ReportExtensionValidator reportExtensionValidator = new ReportExtensionValidator();
        assertFalse(reportExtensionValidator.validate(""));
    }

    @Test
    public void testIfNullEnterByUser() throws Exception{
        ReportExtensionValidator reportExtensionValidator = new ReportExtensionValidator();
        assertFalse(reportExtensionValidator.validate(null));
    }

    @Test
    public void testIfNotExistingValueEnterByUser() throws Exception{
        ReportExtensionValidator reportExtensionValidator = new ReportExtensionValidator();
        assertFalse(reportExtensionValidator.validate("NOT-EXIST"));
    }

    @Test
    public void testIfExistingValueEnterByUser() throws Exception{
        ReportExtensionValidator reportExtensionValidator = new ReportExtensionValidator();
        assertTrue(reportExtensionValidator.validate("XLS"));
    }

}
