package com.drfa.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class ReportDetailValidatorTest {

    @Test
    public void testIfNothingEnterByUser() throws Exception{
        ReportDetailValidator reportDetailValidator = new ReportDetailValidator();
        assertFalse(reportDetailValidator.validate(""));
    }

    @Test
    public void testIfNullEnterByUser() throws Exception{
        ReportDetailValidator reportDetailValidator = new ReportDetailValidator();
        assertFalse(reportDetailValidator.validate(null));
    }

    @Test
    public void testIfNotExistingValueEnterByUser() throws Exception{
        ReportDetailValidator reportDetailValidator = new ReportDetailValidator();
        assertFalse(reportDetailValidator.validate("NOT-EXIST"));
    }

    @Test
    public void testIfExistingValueEnterByUser() throws Exception{
        ReportDetailValidator reportDetailValidator = new ReportDetailValidator();
        assertTrue(reportDetailValidator.validate("SUMMARY"));
        assertTrue(reportDetailValidator.validate("DETAILED"));
        assertTrue(reportDetailValidator.validate("BOTH"));
    }

}
