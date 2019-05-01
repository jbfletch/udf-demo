package com.happypants.ksql.udfdemo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class RecodeBmiTest {

    // Test all BMI Ranges return the correct WHO Category, also test a null input returns the correct value
    @ParameterizedTest
    @CsvSource({",NO_BMI_PROVIDED","18.4,Underweight", "19.0,Normal","25.0,Overweight","30.0,Obese_Class_1","35.0,Obese_Class_2"})
    void recodeBmi_ShouldGiveBackCorrectWHOCategories(Double bmi, String whocat) {
        RecodeBmi recodeBmi = new RecodeBmi();
        String actualValue = recodeBmi.recodeBmi(bmi);
        assertEquals(whocat,actualValue);

    }

}