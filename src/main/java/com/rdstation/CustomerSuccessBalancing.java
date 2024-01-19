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
    Map<Integer, Integer> customerSuccessCustomersMap = new HashMap<>();
    int maxValue = -1;

    customerSuccess.removeIf(obj -> customerSuccessAway.contains(obj.getId()));
    customerSuccess.sort(Comparator.comparing(CustomerSuccess::getScore));

    for (Customer customer : customers) {
      for (CustomerSuccess customerSuccessMember : customerSuccess) {
        if (customerSuccessMember.getScore() >= customer.getScore()) {
          customerSuccessCustomersMap.put(
              customerSuccessMember.getId(),
              customerSuccessCustomersMap.getOrDefault(customerSuccessMember.getId(), 0) + 1);

          break;
        }
      }
    }

    if (!customerSuccessCustomersMap.isEmpty()) {
      maxValue = Collections.max(customerSuccessCustomersMap.values());
    }

    Map<Integer, Integer> keysWithMaxValue = new HashMap<>();
    for (Map.Entry<Integer, Integer> entry : customerSuccessCustomersMap.entrySet()) {
      if (entry.getValue() == maxValue) {
        keysWithMaxValue.put(entry.getKey(), entry.getValue());
      }
    }

    if (customerSuccessCustomersMap.isEmpty() && customerSuccessCustomersMap != null
        || (keysWithMaxValue.size() > 1)) {
      return 0;
    } else {
      return keysWithMaxValue.keySet().iterator().next();
    }
  }
}
