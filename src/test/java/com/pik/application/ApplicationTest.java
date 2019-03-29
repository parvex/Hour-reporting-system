package com.pik.application;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationTest {

    @Test
    public void sumShouldReturnFiveWithOnePlusFour() {
        int result = Application.sum(1, 4);

        assertEquals(5, result);
    }
}