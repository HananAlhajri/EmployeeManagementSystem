package com.coop.employeemanagment.repos;

import com.coop.employeemanagment.infrastructures.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepo extends JpaRepository<Account, Long> {

    Account findOneByUsernameAndPassword(String username, String password);
}
