package jtechlog.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"/applicationContext.xml", "/applicationContext-servlet.xml"})
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getEmployees() throws Exception {
        this.mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void getEmployee() throws Exception {
        this.mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void getEmployeeNotModified() throws Exception {
        String etag = this.mockMvc.perform(get("/api/employees"))
                .andReturn().getResponse().getHeader("ETag");

        this.mockMvc.perform(get("/api/employees")
                .header("If-none-match", etag))
                .andExpect(status().isNotModified());
    }

    @Test
    public void getEmployeeModified() throws Exception {
        String etag = this.mockMvc.perform(get("/api/employees"))
                .andReturn().getResponse().getHeader("ETag");

        this.mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Roe\"}"));

        this.mockMvc.perform(get("/api/employees")
                .header("If-none-match", etag))
                .andExpect(status().isOk());
    }


}
