import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://91.241.64.178:7081/api/users";
        HttpHeaders headers = new HttpHeaders();

        //get
        HttpEntity<String> entityString = new HttpEntity<String>(headers);
        restTemplate.exchange(url, HttpMethod.GET, entityString, String.class)
                .getBody();

        //cookie
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                url, String.class
        );
        List<String> cookies = forEntity.getHeaders().get("Set-Cookie");

        //post
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        User user = new User((long) 3, "James", "Brown", (byte) 18);
        HttpEntity<User> entityUser = new HttpEntity<User>(user ,headers);
        String post = restTemplate.exchange(
                url, HttpMethod.POST, entityUser, String.class).getBody();

        //update
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        user.setName("Thomas");
        user.setLastName("Shelby");
        entityUser = new HttpEntity<User>(user, headers);
        String update = restTemplate.exchange(
                url, HttpMethod.PUT, entityUser, String.class).getBody();

        //delete
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        entityUser = new HttpEntity<User>(headers);
        String delete = restTemplate.exchange(
                url+"/"+3, HttpMethod.DELETE, entityUser, String.class).getBody();

        System.out.println(post + " " + update + " " + delete);
    }
}
