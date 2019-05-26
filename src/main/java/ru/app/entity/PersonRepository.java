package ru.app.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
     List<Person> findAll();
     void  deletePersonById(Integer id);
     List<Person> findByIdIn (List<Integer> idList);
}
