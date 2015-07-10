package com.drfa.engine.expression;

/**
 * Created by Sanjiv on 7/10/2015.
 */
public class ExpressionFactory {
    
    public static Expression expression(String columnType, String expressionType){
        
        if(columnType.equalsIgnoreCase("INTEGER")){
            if(expressionType.equalsIgnoreCase("TP")){
                return new TolerancePercentageExpression();
            }else if(expressionType.equalsIgnoreCase("TA")){
                return new ToleranceAbsoluteExpression();
            }
        }else if(columnType.equalsIgnoreCase("DATE")){
            if(expressionType.equalsIgnoreCase("DR")){
                return new DateRangeExpression();
            }else if(expressionType.equalsIgnoreCase("DF")){
                return new DateFormatExpression();
            }
        }else if(columnType.equalsIgnoreCase("STRING")){
            if(expressionType.equalsIgnoreCase("SP")){
                return new StringParserExpression();
            }
        }
        return new GenericExpression();
    }
}
