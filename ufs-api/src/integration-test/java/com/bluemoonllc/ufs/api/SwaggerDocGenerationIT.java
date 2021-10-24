package com.bluemoonllc.ufs.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SwaggerDocGenerationIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void generateSwagger() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/v3/api-docs")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(result -> Files.write(
            Paths.get("target", "swagger.json"),
            result.getResponse().getContentAsString().getBytes(),
            StandardOpenOption.CREATE));
  }
}
