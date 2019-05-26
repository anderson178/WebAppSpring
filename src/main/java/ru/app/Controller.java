package ru.app;

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
public class Controller {

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
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Person findById(@RequestParam(value = "parm") Integer id) throws Exception {
        return personRepository.findById(id)
                .orElseThrow(Exception::new);
    }


    @Transactional
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove(@RequestBody Integer id) {
        personRepository.deletePersonById(id);
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person add(@RequestBody Person person) {
        int i = 0;
        return personRepository.save(person);
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    public void update(@RequestBody Person person) {
        if (personRepository.findById(person.getId()).isPresent()) {
            person.setComment("Update");
            person.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            personRepository.save(person);
        } else {
            //TODO передать данные на фронт
            System.out.println("not exist is person with id");
        }
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
                    result.append(" Persons with " + listId.get(j) + "add ");

                } else {
                    result.append(" Persons with " + listId.get(j) + "not add ");
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
