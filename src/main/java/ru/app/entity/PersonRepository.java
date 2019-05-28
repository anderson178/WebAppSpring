package ru.app.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
     List<Person> findAllByOrderByIdAsc();
     List<Person> findAll();
//     @Query("select s.id, s.lastName from person as s")
//     List<Object> getSchoolIdAndName();
//     void  deletePersonById(Integer id);
//     List<Person> findByIdIn (List<Integer> idList);
}
