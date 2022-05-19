package com.example;

//import com.example.client.DemoClient;
import com.example.client.DemoClient;
import com.example.mapper.SchoolMapper;
import com.example.po.School;
import com.x.retry.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Slf4j
public class ExampleApplicationTests {

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired(required = false)
    private DemoClient demoClient;

    @Test
    public void demoClient() {
        Result s = demoClient.get();
        System.out.println(s);
    }

    @Test
    public void test() {
        School school = new School();
        school.setAddress("上海");
        school.setName("复旦");
        schoolMapper.insert(school);
    }

    @Test
    public void test1() {
        String template = restTemplate.getForObject("http://127.0.0.1:8088/school/id", String.class);
        System.out.println(template);
    }



}
