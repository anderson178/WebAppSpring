package ru.app;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.app.entity.Person;
import ru.app.entity.PersonRepository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/")
public class Controller {


    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/getAll")
    public List<Person> getStr() {
//        List<Person> rst = new ArrayList<>();
//        personRepository.findAll().forEach(person -> rst.add(person.toString()));
        //List<Person> rst = personRepository.findAll().stream().map(person -> person.setBirthDate(new Date(person.getBirthDate().)));


        return personRepository.findAll();
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
//    @SneakyThrows(InterruptedException.class)
    public String updateMass(@RequestBody List<Integer> listId) {
        //TODO настройки по потокам считывать из файла properties
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        List<Person> personList = personRepository.findByIdIn(listId);
//        personList.forEach(person -> {
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            person.setComment("Update " + timestamp);
//            person.setUpdateDate(timestamp);
//            personRepository.save(person);
//        });

        for (int i = 0; i < listId.size(); i++) {
            final int j = i;
            service.submit(() -> {
                Optional<Person> optional = personRepository.findById(listId.get(j));
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                if (optional.isPresent()) {
                    Person person = optional.get();
                    person.setComment("Update " + timestamp);;
                    person.setUpdateDate(timestamp);
                    personRepository.save(person);
                } else {


                    //TODO передать данные на фронт
                    System.out.println("not exist is person with id");
                }

            });
        }
        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);
        return "WORKS!";
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
