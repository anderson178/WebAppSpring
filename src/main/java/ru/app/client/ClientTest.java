package ru.app.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.app.entity.Person;

import java.util.Date;

public class ClientTest {
    private static final String ADD = "http://127.0.0.1:8080/map/addPerson";
    private static final String BYID = "http://127.0.0.1:8080/map/getById?parm={parm}";
    private static final String REMOVEBYID = "http://127.0.0.1:8080/map/removePerson";
    private static final String UPDATE = "http://127.0.0.1:8080/map/updatePerson";


    public void add(Person person) {
        ResponseEntity<Person> responseEntity = new RestTemplate().postForEntity(
                ADD, person, Person.class);
        System.out.println(responseEntity.getStatusCode());
    }

    public void update (Person person) {
        ResponseEntity<Person> responseEntity = new RestTemplate().postForEntity(
                UPDATE, person, Person.class);
        System.out.println(responseEntity.getStatusCode());
    }

    public void getFindById(Integer id) {
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
               BYID, String.class, id);
        System.out.println(responseEntity.toString());

    }

    public void removeById(Integer id) {
        ResponseEntity<Integer> responseEntity = new RestTemplate().postForEntity(
                REMOVEBYID, id, Integer.class);
        System.out.println(responseEntity.getStatusCode());

    }

    public static void main(String[] args) {
        //new ClientTest().removeById(3);
        new ClientTest().update(new Person(4,"Mironenko", "Denis", "Sergeevich", new Date(1989,10,21)));
        //new ClientTest().getFindById(3);
         //new ClientTest().add(new Person(3,"Mironenko", "Denis", "Sergeevich", new Date(1989,10,21)));
    }

}
