package com.github.can019.base.global.filter;

import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {GenerateThreadContextIdTestController.class},
        properties = {"spring.profiles.active=GENERATE_THREAD_CONTEXT_ID_TEST"})
public class GenerateThreadContextIdFilterTest {
    private MockMvc mockMvc; // 특정 filter만을 target으로 하기 위해 Autowired를 사용하지 않음.

    @Autowired
    protected WebApplicationContext context;

    @AfterEach
    void afterEach(){
        mockMvc = null;
    }


    @Test
    void controller에서_thread_id는_같아야한다() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new GenerateThreadContextIdFilter()).build();
        String randomId = UUID.randomUUID().toString().substring(0,8);

        ThreadContext.put("id", randomId);
        mockMvc.perform(get("/foo"))
                .andExpect(jsonPath("$").value(randomId));
    }

    @Test
    void GenerateThreadContextIdFilter가_먼저_결합된_경우_Thread_context에_id가_존재해야한다() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new GenerateThreadContextIdFilter(), new ThreadContextCheckFilter()).build();

        try {
            mockMvc.perform(get("/foo")).andExpect(status().isOk());
            ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void GenerateThreadContextIdFilter가_나중에_결합된_경우_Thread_context에_id가_존재하면_안된다() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new ThreadContextCheckFilter(), new GenerateThreadContextIdFilter()).build();
        try {
            mockMvc.perform(get("/foo"));
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(ThreadContextCheckFilter.EMPTY_THREAD_CONTEXT_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
