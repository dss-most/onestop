package onestop.repository;

import onestop.model.FileMeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;



public interface FileMetaRepo extends 
	JpaRepository<FileMeta, Long>, QueryDslPredicateExecutor<FileMeta> {

}
