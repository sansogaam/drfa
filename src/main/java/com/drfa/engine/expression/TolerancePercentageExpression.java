package com.drfa.engine.expression;

import org.apache.log4j.Logger;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class TolerancePercentageExpression implements Expression {

    static Logger LOG = Logger.getLogger(TolerancePercentageExpression.class);

    @Override
    public boolean compareValue(String baseValue, String baseExpressionType, String targetValue, String targetExpressionType) {
        try {
            Integer baseIntValue = Integer.parseInt(baseValue);
            double percentageValue = new Double(Double.parseDouble(baseExpressionType) / 100);
            double subtractValue = percentageValue * baseIntValue;
            Integer lhsCalculatedValued = baseIntValue - (int) subtractValue;
            Integer rhsCalculatedValued = baseIntValue + (int) subtractValue;
            Integer targetIntValue = Integer.parseInt(targetValue);
            return (lhsCalculatedValued <= targetIntValue && targetIntValue <= rhsCalculatedValued);
        }catch(Exception e){
            LOG.error(String.format("Error in processing the value %s", e.getMessage()));
            return false;
        }
    }
}
