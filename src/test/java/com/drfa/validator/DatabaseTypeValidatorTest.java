package com.drfa.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sanjiv on 2/27/2015.
 */
public class DatabaseTypeValidatorTest {

    @Test
    public void testIfNothingEnterByUser() throws Exception{
        DatabaseTypeValidator databaseTypeValidator = new DatabaseTypeValidator();
        assertFalse(databaseTypeValidator.validate(""));
    }

    @Test
    public void testIfNullEnterByUser() throws Exception{
        DatabaseTypeValidator databaseTypeValidator = new DatabaseTypeValidator();
        assertFalse(databaseTypeValidator.validate(null));
    }

    @Test
    public void testIfNotExistingValueEnterByUser() throws Exception{
        DatabaseTypeValidator databaseTypeValidator = new DatabaseTypeValidator();
        assertFalse(databaseTypeValidator.validate("NOT-EXIST"));
    }

    @Test
    public void testIfExistingValueEnterByUser() throws Exception{
        DatabaseTypeValidator databaseTypeValidator = new DatabaseTypeValidator();
        assertTrue(databaseTypeValidator.validate("MYSQL"));
    }

    @Test
    public void testIfExistingValueButDifferentNameEnterByUser() throws Exception{
        DatabaseTypeValidator databaseTypeValidator = new DatabaseTypeValidator();
        assertTrue(databaseTypeValidator.validate("MS-SQL"));
    }

    @Test
    public void testIfNotExistingValueEnterByUserButItsKey() throws Exception{
        DatabaseTypeValidator databaseTypeValidator = new DatabaseTypeValidator();
        assertFalse(databaseTypeValidator.validate("MSSQL"));
    }


}
