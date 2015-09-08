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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerTest.class);

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getEmployees() throws Exception {
        this.mockMvc.perform(get("/api/employees")
                .accept(MediaType.parseMediaType("application/json"))
                .contentType(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void getEmployee() throws Exception {
        this.mockMvc.perform(get("/api/employees/1")
                .accept(MediaType.parseMediaType("application/json"))
                .contentType(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }
}
