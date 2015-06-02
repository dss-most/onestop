package onestop.repository;

import onestop.model.Rfq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;


public interface RfqRepo extends JpaRepository<Rfq, Long>, QueryDslPredicateExecutor<Rfq> {

}
