package com.test.atm;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void withdrawCash_whenInsufficientAmount_thenDeny() {
        Atm atm = new Atm();
        atm.atmCashMap.clear();
        atm.atmCashMap.put(500, 1);
        assertThrows(RuntimeException.class, () -> atm.withdrawCash(Integer.MAX_VALUE, "Txn1"));
    }

    @Test
    void withdrawCash_whenAtmBalanceIsZero_thenThrowError() {
        Atm atm = new Atm();
        atm.atmCashMap.clear();
        assertThrows(RuntimeException.class, () -> atm.withdrawCash(500, "Txn1"));
    }

    @Test
    void withdrawCash_whenAmountIsZero_thenThrowError() {
        Atm atm = new Atm();
        assertThrows(RuntimeException.class, () -> atm.withdrawCash(0, "Txn1"));
    }

    @Test
    void withdrawCash_whenAmountIsNotMultipleOf10_thenThrowError() {
        Atm atm = new Atm();
        assertThrows(RuntimeException.class, () -> atm.withdrawCash(0, "Txn1"));
    }

    @Test
    void getAtmBalance_whenDefaultBalance_thenReturn18K() {
        Atm atm = new Atm();
        assertEquals(18_000, atm.getAtmBalance());
    }

    @Test
    void getAtmBalance_whenEmptyBalance_thenReturnZero() {
        Atm atm = new Atm();
        atm.atmCashMap.clear();
        assertEquals(0, atm.getAtmBalance());
    }
}