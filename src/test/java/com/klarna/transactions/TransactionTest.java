package com.klarna.transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author sanray on 9/20/2021
 */
public class TransactionTest {

    @Test
    public void test1() {
        Assert.assertEquals(new ArrayList<>(), Transactions.findRejectedTransactions(new ArrayList<>(), 0));
    }

    @Test
    public void test2() {
        List<String> transactiondata = Arrays.asList("John,Doe,john@doe.com,200,TR0001");
        Assert.assertEquals(new ArrayList<>(), Transactions.findRejectedTransactions(transactiondata, 200));
    }

    @Test
    public void test3() {
        List<String> transactiondata = Arrays.asList("John,Doe,john@doe.com,200,TR0001");
        Assert.assertEquals(Arrays.asList("TR0001"), Transactions.findRejectedTransactions(transactiondata, 199));
    }

    @Test
    public void test4() {
        List<String> transactiondata = Arrays.asList("John,Doe,john@doe.com,199,TR0001",
                "John,Doe,john@doe.com,200,TR0002");
        Assert.assertEquals(Arrays.asList("TR0002"), Transactions.findRejectedTransactions(transactiondata, 200));
    }

    @Test
    public void test5() {
        List<String> transactiondata = Arrays.asList("John,Doe,john@doe.com,199,TR0001",
                "John,Doe,john1@doe.com,200,TR0002",
                "John,Doe,john@doe.com,200,TR0003");
        Assert.assertEquals(Arrays.asList("TR0003"), Transactions.findRejectedTransactions(transactiondata, 200));
    }

    @Test
    public void test6() {
        List<String> transactiondata = Arrays.asList("John,Doe,john@doe.com,199,TR0001",
                "John,Doe,john1@doe.com,199,TR0002",
                "John,Doe,john@doe.com,200,TR0003",
                "John,Doe,john1@doe.com,200,TR0004");
        Assert.assertEquals(Arrays.asList("TR0003", "TR0004"),
                Transactions.findRejectedTransactions(transactiondata, 200));
    }

    @Test
    public void test7() {
        List<String> transactiondata = Arrays.asList("John,Doe,john@doe.com,400,TR0001",
                "John,Doe,john1@doe.com,199,TR0002",
                "John,Doe,john@doe.com,200,TR0003",
                "John,Doe,john1@doe.com,200,TR0004");
        Assert.assertEquals(Arrays.asList("TR0001", "TR0004"),
                Transactions.findRejectedTransactions(transactiondata, 200));
    }
}
