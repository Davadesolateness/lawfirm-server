package com.lawfirm.lawfirmserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lawfirm", "ins.framework"})
public class LawfirmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LawfirmServerApplication.class, args);
    }

}
