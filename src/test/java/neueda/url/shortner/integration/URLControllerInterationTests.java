package neueda.url.shortner.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.url.shortner.NeuedaURLShortnerApplication;
import com.neueda.url.shortner.model.Url;
import com.neueda.url.shortner.service.URLService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NeuedaURLShortnerApplication.class)
@AutoConfigureMockMvc
public class URLControllerInterationTests {

	@MockBean
	private URLService urlService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HttpServletResponse response;

	private Url url;

	private final String original = "https://www.google.com/";

	private final String small = "http://bit.ly/qqqqq";

	private List<String> urls;

	ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setup() throws IOException {
		url = new Url();
		url.setShortUrl(small);
		url.setOriginalUrl(original);
		urls = new ArrayList();
		urls.add(original);
	}

	@WithMockUser(username = "user", password = "password")
	@Test
	public void createShortUrl_success() throws Exception {
		Mockito.when(urlService.createShortUrl(urls)).thenReturn(List.of(url));

		mockMvc.perform(
				post("/url/short").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(urls)))
				.andExpect(status().isOk());
	}

}
