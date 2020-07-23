package com.coditas.brewary.app;


import com.coditas.brewary.BeverageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrewApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrewApp.class);

    public static void main(String[] args) {

        BeverageFactory factory = new BeverageFactory();

//       Please pass your order in the 
        String InputOrder = "Chai,-milk, -sugar";

        final double cost = factory.getInvoiceFromOrder(InputOrder);

        LOGGER.info("Total Bill is ${}", cost);

    }


}
