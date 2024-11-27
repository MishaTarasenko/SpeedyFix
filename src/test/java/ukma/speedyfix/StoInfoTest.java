package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ukma.speedyfix.controller.StoController;
import ukma.speedyfix.security.JwtAuthenticationFilter;
import ukma.speedyfix.security.JwtTokenProvider;
import ukma.speedyfix.service.info.StoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoController.class)
public class StoInfoTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoService service;

    @MockBean
    private JwtAuthenticationFilter filter;

    @MockBean
    private JwtTokenProvider provider;

    @Test
    @WithMockUser
    void infoSto() throws Exception {
        this.mockMvc.perform(get("/public/api/info").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
