package dev.alvarogil.portfolio.adapters.web;

import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TestController.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturnsNoContent() throws Exception {
        mockMvc.perform(get("/test").with(user("testuser")))
                .andExpect(status().isNoContent());
    }
}
