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
        Person person = null;
        if (optional.isPresent()) {
            person = optional.get();
            personRepository.delete(person);
        } else {
            person = new Person(-1);
        }
        return person;
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String add(@RequestBody Person person) throws Exception {
        String rst = null;
        if (person.getId() != null) {
            Optional<Person> optional = personRepository.findById(person.getId());
            if (!optional.isPresent()) {
                personRepository.save(person);
                rst = person.toString() + " =  add";
            } else {
                rst = person.toString() + " = there is already a person with such id";
            }
        } else {
            Person p = new Person();
            p.setLastName(person.getLastName());
            p.setFirstName(person.getFirstName());

            personRepository.save(p);
            rst = person.toString() + " =  add";
        }
        return rst;
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String update(@RequestBody Person person) {
        String rst = null;
        if (personRepository.findById(person.getId()).isPresent()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            person.setComment("Update " + timestamp);
            person.setUpdateDate(timestamp);
            personRepository.save(person);
            rst = person.toString() + " =  update";
        } else {
            //TODO передать данные на фронт
            rst = person.toString() + "not exist is person with id";
        }
        return rst;
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