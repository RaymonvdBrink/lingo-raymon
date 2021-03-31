package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CiTestConfiguration.class)
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Start een nieuw game plus een nieuwe ronde")
    void testNewGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/trainer/game/newGame");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.hints", hasSize(1)))
                .andExpect(jsonPath("$.roundNumber", is(1)))
        ;
    }
}