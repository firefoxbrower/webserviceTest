package com.mybank.xfire.example;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BankingService  implements IBankingService {
   

    public BankingService(){ } ;

    @Override
    public String transfeFunds(String fromAccount, String toAccount, double amount, String currency) {
        String statusMessage = "";
        try {
            NumberFormat formatter = new DecimalFormat("###,###,###,###.00");
            statusMessage = "COMPLETED: " + currency + " " + formatter.format(amount)+ " was successfully transferred from A/C# " + fromAccount + " to A/C# " + toAccount;
        } catch (Exception e){
            statusMessage = "BankingService.transferFunds(): EXCEPTION: " + e.toString();
        }
        
        return statusMessage;
    }
   
}
