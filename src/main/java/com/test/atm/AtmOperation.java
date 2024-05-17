package com.test.atm;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AtmOperation {

    private final Atm atm;

    public AtmOperation(Atm atm) {
        this.atm = atm;
    }

    public void withDrawCash(int amount, String txnId) {
        System.out.println("TxnId: " + txnId + " Amount: " + amount);
        Map<String, List<WithDrawTxn>> withDrawnCash = atm.withDrawCash(amount, txnId);
        for (Map.Entry<String, List<WithDrawTxn>> cashWithdraw : withDrawnCash.entrySet()) {
            System.out.println("TxnId: " + cashWithdraw.getKey() + " countWithdrawn: " + cashWithdraw.getValue());
        }
    }
}
