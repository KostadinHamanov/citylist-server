package com.citylist.server.contoller;

import com.citylist.server.dto.CityDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CityControllerTest.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateCity_testRequestMatching() throws Exception {
        CityDTO cityDTO = new CityDTO();

        mockMvc.perform(put("/cities/{id}", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(cityDTO)))
                .andExpect(status().isOk());
    }
}
