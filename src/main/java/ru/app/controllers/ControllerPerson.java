package ru.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.app.entity.Person;
import ru.app.entity.PersonRepository;
import ru.app.exceptions.NoSuchPerson;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


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


    @GetMapping(value = "/getAll")
    public List<Person> getStr() {
//        List<Person> rst = new ArrayList<>();
//        personRepository.findAll().forEach(person -> rst.add(person.toString()));
        //List<Person> rst = personRepository.findAll().stream().map(person -> person.setBirthDate(new Date(person.getBirthDate().)));


        return personRepository.findAllByOrderByIdAsc();
    }

    /*@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable Integer id) {
        return personRepository.findById(id).toString();
    }*/
    @RequestMapping(value = "/findById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person findById(@RequestBody Integer id) {
        return personRepository.findById(id).orElse(new Person(-1));
    }

//    @Transactional
//    @RequestMapping(value = "/removePerson", method = RequestMethod.POST)
//    public String remove(@RequestParam(name = "id") Integer id) {
//        Optional<Person> optional = personRepository.findById(id);
//        String rst = null;
//        if (optional.isPresent()) {
//            Person person = optional.get();
//            rst = person.toString() + " = remove";
//            personRepository.delete(person);
//        } else {
//            rst = "Person with id not exist";
//        }
//        return rst;
//    }

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

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person add(@RequestBody Person person) throws Exception {
        Person result = null;
            Optional<Person> optional = personRepository.findById(person.getId());
            if (!optional.isPresent()) {
                personRepository.save(person);
                result = person;
            } else {
                result = new Person(-1);
            }
        return result;
    }

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
            // rst = person.toString() + "not exist is person with id";
        }
        return result;
    }

    @RequestMapping(value = "/updatePersons", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String updateMass(@RequestBody List<Integer> listId) {
        StringBuilder result = new StringBuilder();
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
                    result.append(" Persons with " + listId.get(j) + " = update ");

                } else {
                    result.append(" Persons with " + listId.get(j) + " = not update ");
                    // log.error("not exist is person with id");
                    // throw new NoSuchPerson("not exist is person with id");
                }
            });
        }
        service.shutdown();
        try {
            service.awaitTermination(this.await, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
        return result.toString();
    }


    /**
     * Method that forms the current date and time
     *
     * @return
     */
    private String getCurrenntDataTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }


}