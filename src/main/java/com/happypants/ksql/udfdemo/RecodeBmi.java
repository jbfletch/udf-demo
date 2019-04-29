package com.happypants.ksql.udfdemo;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import java.util.Optional;

@UdfDescription(name = "recodebmi", description = "takes continuous BMI readings and translates to WHO categories")
public class RecodeBmi {

    // Create a ImmutableRangeMap to hold the WHO Classification mappings
    RangeMap<Double, String> bmiRangeMap
        = new ImmutableRangeMap.Builder<Double,String>()
            .put(Range.singleton(-1.0),"NO_BMI_PROVIDED")
            .put(Range.closedOpen(0.0,18.5),"Underweight")
            .put(Range.closedOpen(18.5,25.0), "Normal")
            .put(Range.closedOpen(25.0,30.0),"Overweight")
            .put(Range.closedOpen(30.0,35.0),"Obese_Class_1")
            .put(Range.closedOpen(35.0,40.0),"Obese_Class_2")
            .put(Range.atLeast(40.0),"Obese_Class_3")
            .build();

    @Udf(description = "returns who category for a given Double BMI reading or NO_BMI_PROVIDED when null")
    public String recodeBmi(final Double v1) {
        // Code all nulls to a predefined number signifying a data intake error
        double v2 = Optional.ofNullable(v1).orElse(-1.0);
        return bmiRangeMap.get(v2);
    }

    @Udf(description = "returns who category for a given Double BMI reading, does not allow nulls")
    public String recodeBmi(final double v1) {
        return bmiRangeMap.get(v1);
    }

//   Testing null logic
//    public static void main(String[] args) {
//        System.out.println(recodeBmi(null));
//        System.out.println(recodeBmi(20.0));
//    }

}

