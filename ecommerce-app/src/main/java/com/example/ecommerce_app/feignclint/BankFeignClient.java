package com.example.ecommerce_app.feignclint;


import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value="bankdetails",url="http://localhost:8091/bank")

@FeignClient(name = "BANKING-APP") 
public interface BankFeignClient {

    @PostMapping("/bank/fundTransfer") 
    String doTransaction(@RequestParam("fromAccount") Long fromAccount,@RequestParam("toAccount") Long toAccount,@RequestParam("amount") BigDecimal amount
    );
}

