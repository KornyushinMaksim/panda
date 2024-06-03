package com.panda.repository;

import com.panda.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

//    @Query("""
//            FROM employees e JOIN FETCH
//                        (FROM authentications a WHERE a.login = :username)
//                        WHERE e.employee_id = a.employee_id
//            """)
    Optional<Employee> findEmployeeByAuthenticationLogin(String login);
}
