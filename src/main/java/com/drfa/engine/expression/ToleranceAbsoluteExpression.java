package com.drfa.engine.expression;

import org.apache.log4j.Logger;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class ToleranceAbsoluteExpression implements Expression {

    static Logger LOG = Logger.getLogger(ToleranceAbsoluteExpression.class);

    @Override
    public boolean compareValue(String baseValue, String baseExpressionType, String targetValue, String targetExpressionType) {
        try {
            Integer intValue = Integer.parseInt(baseValue);
            Integer deltaFactor = Integer.parseInt(baseExpressionType);
            Integer lhsCalculatedValued = intValue - deltaFactor;
            Integer rhsCalculatedValued = intValue + deltaFactor;
            Integer targetIntValue = Integer.parseInt(targetValue);
            return (lhsCalculatedValued <= targetIntValue && targetIntValue <= rhsCalculatedValued);
        }catch(Exception e){
            LOG.error(String.format("Error in processing the value %s", e.getMessage()));
            return false;
        }
    }
}
