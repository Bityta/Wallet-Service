package org.example.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    void nameTest() {

        assertEquals("credit", Operation.credit.toString());
        assertEquals("replenishment", Operation.replenishment.toString());
        assertEquals("withdraw", Operation.withdraw.toString());

    }
}