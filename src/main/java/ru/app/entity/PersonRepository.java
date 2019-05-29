package ru.app.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Денис Мироненко
 * @version $Id$
 * @since 29.05.2019
 */

public interface PersonRepository extends CrudRepository<Person, Integer> {
     List<Person> findAllByOrderByIdAsc();
     List<Person> findAll();
}
