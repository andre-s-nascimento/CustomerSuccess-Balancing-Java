package com.rdstation;

import java.util.*;

public class CustomerSuccessBalancing {

  private final List<CustomerSuccess> customerSuccess;
  private final List<Customer> customers;
  private final List<Integer> customerSuccessAway;

  public CustomerSuccessBalancing(
      List<CustomerSuccess> customerSuccess,
      List<Customer> customers,
      List<Integer> customerSuccessAway) {
    this.customerSuccess = customerSuccess;
    this.customers = customers;
    this.customerSuccessAway = customerSuccessAway;
  }

  public int run() {


    System.out.println("CS " + customerSuccess);
    System.out.println("CSAway " + customerSuccessAway);
    System.out.println("C " + customers);

    Map<Integer, Integer> customerSuccessCustomersMap = new HashMap<>();

    customerSuccess.removeIf(obj -> customerSuccessAway.contains(obj.getId()));
    customerSuccess.sort(Comparator.comparing(CustomerSuccess::getScore));

    for (Customer customer : customers) {
      for (CustomerSuccess customerSuccessMember : customerSuccess) {

        if (customerSuccessMember.getScore() >= customer.getScore()) {
          System.out.println(
              "Para o Customer["
                  + customer.getId()
                  + "] com Customer Score: "
                  + customer.getScore());
          System.out.println(
              "deve atender o CS["
                  + customerSuccessMember.getId()
                  + "] com o CS Score["
                  + customerSuccessMember.getScore()
                  + "]");
          System.out.println(
              "==========================================================================");

          customerSuccessCustomersMap.put(
              customerSuccessMember.getId(),
              customerSuccessCustomersMap.getOrDefault(customerSuccessMember.getId(), 0) + 1);

          break;
        }
      }
    }

    for (Map.Entry<Integer, Integer> cs : customerSuccessCustomersMap.entrySet()) {
      System.out.println("CSMAP Key = " + cs.getKey() + ", Value = " + cs.getValue());
    }
    int maxValue = -1;

    if (!customerSuccessCustomersMap.isEmpty()) {
      maxValue = Collections.max(customerSuccessCustomersMap.values());
    }

    Map<Integer, Integer> keysWithMaxValue = new HashMap<>();
    for (Map.Entry<Integer, Integer> entry : customerSuccessCustomersMap.entrySet()) {
      if (entry.getValue() == maxValue) {
        keysWithMaxValue.put(entry.getKey(), entry.getValue());
      }
    }

    if (!keysWithMaxValue.isEmpty()) {

      for (Map.Entry<Integer, Integer> cs : keysWithMaxValue.entrySet()) {
        System.out.println("KWMV Key = " + cs.getKey() + ", Value = " + cs.getValue());
      }
    }

    if (customerSuccessCustomersMap.isEmpty() && customerSuccessCustomersMap != null
        || (keysWithMaxValue.size() > 1)) {
      return 0;
    } else {
      System.out.println(
          "Id do CS que atende mais clientes é: " + keysWithMaxValue.keySet().iterator().next());
      return keysWithMaxValue.keySet().iterator().next();
      // ((java.lang.Integer)((java.util.Map$Entry)keysWithMaxValue.entrySet().toArray()[0]).getValue()).value
    }
  }
}
