package com.mybank.xfire.example;

public interface IBankingService {

    String transfeFunds(String fromAccount, String toAccount, double amount, String currency);
}
