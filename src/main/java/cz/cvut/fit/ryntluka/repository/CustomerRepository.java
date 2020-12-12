package cz.cvut.fit.ryntluka.repository;

import cz.cvut.fit.ryntluka.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByLastName(String lastName);
}
