package com.test.atm;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AtmTest {

    @Test
    void withdrawCash_whenSinglePermissibleAmount_thenPermit() {
        Atm atm = new Atm();
        Map<String, List<WithDrawTxn>> response = atm.withdrawCash(500, "Txn1");
        // Deducted ATM cash
        assertEquals(19, atm.atmCashMap.get(500));
        // Withdrawn ATM cash
        assertEquals(1, response.get("Txn1").size());
    }

    @Test
    void withdrawCash_whenMultiplePermissibleAmount_thenPermit() {
        Atm atm = new Atm();
        Map<String, List<WithDrawTxn>> response = atm.withdrawCash(700, "Txn1");
        // Deducted ATM cash
        assertEquals(19, atm.atmCashMap.get(500));
        assertEquals(29, atm.atmCashMap.get(200));
        // Withdrawn ATM cash
        assertEquals(2, response.get("Txn1").size());
    }

    @Disabled
    @Test
    void withdrawCash_whenInsufficientAmount_thenDeny() {
        Atm atm = new Atm();
        assertThrows(RuntimeException.class, () -> atm.withdrawCash(10000, "Txn1"));
    }

    @Disabled
    @Test
    void getAtmBalance() {
    }
}