package ru.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.app.entity.Person;
import ru.app.entity.PersonRepository;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/map")
public class Controller {
    private static final String QUERY_GET_TEN = "SELECT last_name, first_name FROM person";
    private SqlRowSet srs;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<String> getStr() {
        List<String> rst = new ArrayList<>();
        personRepository.findAll().forEach(person -> rst.add(person.toString()));
        return rst;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "parm") Integer id) {
       return personRepository.findById(id).toString();
    }


    @Transactional
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove(@RequestBody Integer id) {
        personRepository.deletePersonById(id);
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public void add(@RequestBody Person person) {
        personRepository.save(person);
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    public void update(@RequestBody Person person) {
        List<Integer> listId = personRepository.findAll().stream().map(Person::getId).collect(Collectors.toList());
        if (listId.contains(person.getId())) {
            person.setComment("Update");
            person.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            personRepository.save(person);
        } else {
            System.out.println("not exist is person with id");
        }
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
