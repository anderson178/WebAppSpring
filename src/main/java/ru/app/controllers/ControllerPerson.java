package ru.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import ru.app.entity.Person;
import ru.app.entity.PersonRepository;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Денис Мироненко
 * @version $Id$
 * @since 29.05.2019
 */

@RestController
@Slf4j
@RequestMapping(value = "/")
@PropertySource("classpath:threads.properties")
public class ControllerPerson {

    @Value("${maxthreads}")
    private int maxthreads;

    @Value("${await}")
    private int await;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Метод возвращает из таблицы все записи
     *
     * @return - список элементов
     */
    @GetMapping(value = "/getAll")
    public List<Person> getStr() {
        return personRepository.findAllByOrderByIdAsc();
    }

    /**
     * Метод возвращает Person по id
     *
     * @param id - id запрашиваемого Person
     * @return - person. Если Perыщт по запрашиваемому id не был найжден то вернет Person c id = -1
     */
    @RequestMapping(value = "/findById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person findById(@RequestBody Integer id) {
        return personRepository.findById(id).orElse(new Person(-1));
    }

    /**
     * Метод удаляет Person по id
     *
     * @param id - id запрашиваемого Person
     * @return - person. Если Perыщт по запрашиваемому id не был найжден то вернет Person c id = -1
     */
    @RequestMapping(value = "/removePerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person remove(@RequestBody Integer id) {
        Optional<Person> optional = personRepository.findById(id);
        Person result = null;
        if (optional.isPresent()) {
            result = optional.get();
            personRepository.delete(result);
        } else {
            result = new Person(-1);
        }
        return result;
    }

    /**
     * Метод возвращает Person по id
     *
     * @param id - id запрашиваемого Person
     * @return - person. Если Perыщт по запрашиваемому id не был найжден то вернет Person c id = -1. Если
     * был достигнут лимит индексов то вернет Person с id = -2
     */
    @RequestMapping(value = "/addPerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person add(@RequestBody Person person) {
        Person result = null;
        if (person.getId() == null) {
            Integer id = this.idGenerated();
            if (id != null) {
                person.setId(id);
                result = person;
                personRepository.save(person);
            } else {
                result = new Person(-2);
            }
        } else {
            Optional<Person> optional = personRepository.findById(person.getId());
            if (!optional.isPresent()) {
                personRepository.save(person);
                result = person;
            } else {
                result = new Person(-1);
            }
        }
        return result;
    }

    /**
     * Метод генерации id
     *
     * @return - id
     */
    private Integer idGenerated() {
        List<Integer> listId = personRepository.findAll().stream().map(Person::getId).collect(Collectors.toList());
        Integer result = null;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (!listId.contains(i)) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Метод обновляет поля Person по id
     *
     * @param id - id запрашиваемого Person
     * @return - person. Если Perыщт по запрашиваемому id не был найжден то вернет Person c id = 1
     */
    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person update(@RequestBody Person person) {
        Person result = null;
        if (personRepository.findById(person.getId()).isPresent()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            person.setComment("Update " + timestamp);
            person.setUpdateDate(timestamp);
            personRepository.save(person);
            result = person;
        } else {
            result = new Person(-1);
        }
        return result;
    }

    /**
     * Метод обновляет поля Person по id для каждого Person в отдельном потоке
     *
     * @param listId
     */
    @RequestMapping(value = "/updatePersons", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateMass(@RequestBody List<Integer> listId) {
        ExecutorService service = Executors.newFixedThreadPool(this.maxthreads);
        for (int i = 0; i < listId.size(); i++) {
            final int j = i;
            service.submit(() -> {
                Optional<Person> optional = personRepository.findById(listId.get(j));
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                if (optional.isPresent()) {
                    Person person = optional.get();
                    person.setComment("Update " + timestamp);
                    person.setUpdateDate(timestamp);
                    personRepository.save(person);
                }
            });
        }
        service.shutdown();
        try {
            service.awaitTermination(this.await, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

}