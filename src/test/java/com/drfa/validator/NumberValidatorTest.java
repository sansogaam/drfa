package com.drfa.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class NumberValidatorTest {

    @Test
    public void testIfNothingIsEntered() throws Exception{
        NumberValidator numberValidator = new NumberValidator();
        assertFalse(numberValidator.validate(""));
    }

    @Test
    public void testIfNullIsEntered() throws Exception{
        NumberValidator numberValidator = new NumberValidator();
        assertFalse(numberValidator.validate(null));
    }

    @Test
    public void testIfStringIsEntered() throws Exception{
        NumberValidator numberValidator = new NumberValidator();
        assertFalse(numberValidator.validate("ABC"));
    }

    @Test
    public void testIfDoubleStringIsEntered() throws Exception{
        NumberValidator numberValidator = new NumberValidator();
        assertFalse(numberValidator.validate("2.5"));
    }

    @Test
    public void testIfNegativeStringIsEntered() throws Exception{
        NumberValidator numberValidator = new NumberValidator();
        assertFalse(numberValidator.validate("-2"));
    }

    @Test
    public void testIfZeroStringIsEntered() throws Exception{
        NumberValidator numberValidator = new NumberValidator();
        assertTrue(numberValidator.validate("0"));
    }

    @Test
    public void testIfIntegerStringIsEntered() throws Exception{
        NumberValidator numberValidator = new NumberValidator();
        assertTrue(numberValidator.validate("5"));
    }

}
