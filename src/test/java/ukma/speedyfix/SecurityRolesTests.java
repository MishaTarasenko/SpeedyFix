package ukma.speedyfix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityRolesTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthorizedAccessPublicApi() throws Exception {
        mockMvc.perform(get("/public/api/operation"))
                .andExpect(status().isOk());
    }

    @Test
    void unauthorizedAccessAuthApi() throws Exception {
        mockMvc.perform(get("/auth/api/customer"))
                .andExpect(status().is(403));
    }

    @Test
    void unauthorizedAccessAdminApi() throws Exception {
        mockMvc.perform(get("/admin/api/employee"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = "USER")
    void customerAccessPublicApi() throws Exception {
        mockMvc.perform(get("/public/api/operation"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void customerAccessAuthApi() throws Exception {
        mockMvc.perform(get("/auth/api/customer"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void customerAccessAdminApi() throws Exception {
        mockMvc.perform(get("/admin/api/employee"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminAccessPublicApi() throws Exception {
        mockMvc.perform(get("/public/api/operation"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminAccessAuthApi() throws Exception {
        mockMvc.perform(get("/auth/api/customer"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminAccessAdminApi() throws Exception {
        mockMvc.perform(get("/admin/api/employee"))
                .andExpect(status().isOk());
    }
}
