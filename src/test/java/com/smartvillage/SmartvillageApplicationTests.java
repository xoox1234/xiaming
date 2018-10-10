package com.smartvillage;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.smartvillage.entity.Product;
import com.smartvillage.entity.User;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartvillageApplicationTests {

	@Test
	public void contextLoads() {
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		String path = "{module}/app/{method}";
		Product product = new Product();
		product.setName("cs");
		product.setProductId("1324");
		try {
			 ParameterizedTypeReference<Result<List<User>>> typeReference = new ParameterizedTypeReference<Result<List<User>>>() {
	            };
	            UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port("8080").path("{module}/app/{method}").build();
	            ResponseEntity<Result<List<User>>> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST,
	                    new HttpEntity<>(product), typeReference, "sys", "list");
	            Object listBody = responseEntity.getBody().getData();
	            System.out.println(listBody.getClass());
	            if (listBody instanceof List) {
	                List list = (List) listBody;
	                if (list.size() > 0) {
	                    System.out.println(list.get(0).getClass());
	                }
	            }
	            System.out.println(JSON.toJSONString(listBody));

	            String url = "http://localhost:8080/" + path;
	            ResponseEntity<Result> userResponseEntity = restTemplate.postForEntity(url, product, Result.class, "sys", "login");
	            Object loginBody = userResponseEntity.getBody().getData();
	            System.out.println("==========="+loginBody.getClass());
	            System.out.println(JSON.toJSONString(loginBody));
		} catch (Exception e) {
			// TODO: handle exception
		}

}
}