package ru.app.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.app.entity.Person;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientTest {
    private static final String ADD = "http://127.0.0.1:8080/addPerson";
    private static final String BYID = "http://127.0.0.1:8080/getById?parm={parm}";
    private static final String REMOVEBYID = "http://127.0.0.1:8080/removePerson";
    private static final String UPDATE = "http://127.0.0.1:8080/updatePerson";


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
       // Date date = new Date()
        //new ClientTest().removeById(3);
        //new ClientTest().update(new Person(6,"dsf", "df", "dfal", new Date(1989,10,21)));
        //new ClientTest().getFindById(3);
         //new ClientTest().add(new Person(6, "Hihio", "Vasia", "Sergeevich", new Date(1989,10,21)));
        //System.out.println(new Timestamp(System.currentTimeMillis()).toString());
    }

}
