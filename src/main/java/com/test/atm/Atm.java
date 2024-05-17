package com.test.atm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class Atm {

    Map<Integer, Integer> atmCashMap = new TreeMap<>();
    ReentrantLock lock = new ReentrantLock();
    public Atm() {
        atmCashMap.put(500, 20);
        atmCashMap.put(200, 30);
        atmCashMap.put(100, 20);
    }

    public Map<String, List<WithDrawTxn>> withDrawCash(int amount, String TxnId) {
        lock.lock();
        Map<String, List<WithDrawTxn>> withdrawnCash = new HashMap<>();
        try {
            //validate atm balance then proceed with withdrawal
            validateAmountinATM(amount);
            // Get denominations in reverse order, since we need to consume higher ones first
            List<Integer> atmdenominations = atmCashMap.keySet().stream().sorted(Comparator.reverseOrder()).toList();
            List<WithDrawTxn> denomWithDrawList = new ArrayList<>();
            for (Integer denom : atmdenominations) {
                if (atmCashMap.get(denom) != 0 && amount != 0) {
                    int maxDenomCount = amount / denom;
                    int denCount = Math.min(maxDenomCount, atmCashMap.get(denom));
                    amount -= (denCount * denom);
                    denomWithDrawList.add(new WithDrawTxn(denom, denCount));
                }
                withdrawnCash.put(TxnId, denomWithDrawList);
            }
            //Update atm Cash in Atm Map after withdrawal
            withdrawnCash.values().forEach(
                    withDrawTxns -> withDrawTxns.forEach(txn ->
                            atmCashMap.put(txn.denomination(), atmCashMap.get(txn.denomination()) - txn.denCount())));
        } finally {
            lock.unlock();
        }
        return withdrawnCash;
    }

    private void validateAmountinATM(int amount) {
        int balance = atmCashMap.entrySet().stream().mapToInt(den -> den.getKey() * den.getValue()).sum();
        if (balance == 0) {
            throw new RuntimeException("There is no cash in ATM");
        }
        if (balance < amount) {
            throw new RuntimeException("Atm balance is low");
        }
    }
    public int getAtmBalance() {
        return atmCashMap.entrySet().stream().mapToInt(den -> den.getKey() * den.getValue()).sum();
    }
}
