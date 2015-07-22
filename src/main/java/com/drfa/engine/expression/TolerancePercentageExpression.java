package com.drfa.engine.expression;

import com.drfa.engine.file.ExpressionContext;
import org.apache.log4j.Logger;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class TolerancePercentageExpression implements Expression {

    static Logger LOG = Logger.getLogger(TolerancePercentageExpression.class);

    @Override
    public boolean compareValue(String baseValue, String targetValue, ExpressionContext expressionContext) {
        try {
            Integer baseIntValue = Integer.parseInt(baseValue);
            double percentageValue = new Double(Double.parseDouble(expressionContext.getRangeExpression()) / 100);
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
