package com.klarna.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;


public class Transactions {

    public static List<String> findRejectedTransactions(List<String> transactions, int creditLimit) {
        if (null == transactions || transactions.isEmpty()) {
            return new ArrayList<>();
        }
        final List<TransactionData> transactionDataList = transactions.stream().map(data -> new TransactionData((data)))
                .collect(Collectors.toList());

        final Map<String, List<TransactionData>> uniqueCustomers = transactionDataList.stream()
                .collect(Collectors.groupingBy(TransactionData::getUniqueId));

        List<String> rejectedTransId = new ArrayList<>();
        uniqueCustomers.entrySet().stream().forEach(customerTransList -> {
            int availableCredit = creditLimit;
            for (TransactionData data : customerTransList.getValue()) {
                if (availableCredit < data.getAmount()) {
                    rejectedTransId.add(data.getTransId());
                } else {
                    availableCredit -= data.getAmount();
                }
            }
        });

        return rejectedTransId;
    }
}

class TransactionData {
    private String fname;
    private String lname;
    private String email;
    private Integer amount;
    private String transId;

    public Integer getAmount() {
        return this.amount;
    }

    public String getTransId() {
        return this.transId;
    }

    public TransactionData(String data) {
        String[] tokens = data.split(",");
        this.fname = tokens[0];
        this.lname = tokens[1];
        this.email = tokens[2];
        this.amount = Integer.valueOf(tokens[3]);
        this.transId = tokens[4];
    }

    public boolean equals(TransactionData other) {
        if (null == other) {
            return false;
        }
        return StringUtils.equals(this.fname, other.fname) &&
                StringUtils.equals(this.lname, other.lname) &&
                StringUtils.equals(this.email, other.email);
    }

    public String getUniqueId() {
        return String.format("%s|%s|%s", this.fname, this.lname, this.email);
    }
}


