package ru.app.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.app.entity.Person;

import java.util.Date;

public class ClientTest {
    private static final String URL = "http://127.0.0.1:8080/map/addPerson";
    private static final String URLBYID = "http://127.0.0.1:8080/map/getById?parm={parm}";
    private static final String URLREMOVEBYID = "http://127.0.0.1:8080/map/remove";


    public void toSend(Person person) {
        ResponseEntity<Person> responseEntity = new RestTemplate().postForEntity(
                URL, person, Person.class);
        System.out.println(responseEntity.getStatusCode());
    }

    public void getFindById(Integer id) {
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
                URLBYID, String.class, id);
        System.out.println(responseEntity.toString());

    }

    public void removeById(Integer id) {
        ResponseEntity<Integer> responseEntity = new RestTemplate().postForEntity(
                URLREMOVEBYID, id, Integer.class);
        System.out.println(responseEntity.getStatusCode());

    }

    public static void main(String[] args) {
        new ClientTest().removeById(3);
        //new ClientTest().getFindById(3);
         //new ClientTest().toSend(new Person(3,"Mironenko", "Denis", "Sergeevich", new Date(1989,10,21)));
    }

}
