package com.bill.repository;

import com.bill.entity.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill,Long> {

    List<Bill> getBillByAccountId(Long accountId);
}
