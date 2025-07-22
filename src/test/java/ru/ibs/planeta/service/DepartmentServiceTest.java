package ru.ibs.planeta.service;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;
import ru.ibs.planeta.jpa.repo.DepartmentRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class DepartmentServiceTest {

    @RegisterExtension
    public static WireMockExtension wireMockRule = WireMockExtension.newInstance()
        .options(wireMockConfig().port(4567))
        .build();

    @Autowired
    private  DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Value("classpath:responses/departments.json")
    private Resource departmentsResource;

    @Value("classpath:responses/projects.json")
    private Resource projectsResource;

    @BeforeEach
    public void init() {
        wireMockRule.stubFor(get(urlEqualTo("/departments"))
            .willReturn(okJson(loadMockResponse(departmentsResource))));
        wireMockRule.stubFor(get(urlEqualTo("/projects"))
            .willReturn(okJson(loadMockResponse(projectsResource))));
    }

    @Test
    public void loadDepartments() throws IOException, ExecutionException, InterruptedException {
        departmentService.loadDepartments();

        Assertions.assertEquals(10, departmentRepository.findAll().size());
    }

    public String loadMockResponse(Resource resource) {
        try {
            return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
