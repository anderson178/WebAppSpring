package ru.app.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {
     List<Person> findAll();
     List<Person> findById(Integer id);
     void  deletePersonById(Integer id);

}
