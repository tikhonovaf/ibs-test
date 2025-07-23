package ru.ibs.planeta.service;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;
import ru.ibs.planeta.jpa.repo.DepartmentRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 4567)
public class DepartmentServiceTest {

    private static final long UPDATED_DEPARTMENT_ID = 1789L;
    private static final int INITIAL_DEPARTMENT_COUNT = 10;
    private static final int DELETED_DEPARTMENT_COUNT = 7;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectService projectService;

    @Value("classpath:responses/departmentsInit.json")

    private Resource departmentsResourceInit;

    @Value("classpath:responses/departmentsDeleted.json")
    private Resource departmentsResourceDeleted;

    @Value("classpath:responses/departmentsUpdated.json")
    private Resource departmentsResourceUpdated;

    @Value("classpath:responses/projects.json")
    private Resource projectsResource;

    @BeforeEach
    public void init() {
        WireMock.stubFor(get(urlEqualTo("/projects"))
                .willReturn(okJson(loadMockResponse(projectsResource))));
    }

    @Test
    public void loadDepartments() throws IOException, ExecutionException, InterruptedException {
        //  Начальная загрузка
        stubDepartmentsEndpoint(departmentsResourceInit);
        departmentService.loadDepartments().get();
        Assertions.assertEquals(INITIAL_DEPARTMENT_COUNT, departmentRepository.findAll().size());

        //  Загрузка с изменением
        stubDepartmentsEndpoint(departmentsResourceUpdated);
        departmentService.loadDepartments().get();
        Assertions.assertTrue(
                departmentRepository.findById(UPDATED_DEPARTMENT_ID)
                        .orElseThrow(() -> new AssertionError("Department not found"))
                        .getCode()
                        .contains("updated")
        );

        //  Загрузка с удалением
        stubDepartmentsEndpoint(departmentsResourceDeleted);
        departmentService.loadDepartments().get();
        ;
        Assertions.assertEquals(DELETED_DEPARTMENT_COUNT, departmentRepository.findAll().size());

    }

    private void stubDepartmentsEndpoint(Resource resource) {
        WireMock.stubFor(get(urlEqualTo("/departments"))
                .willReturn(okJson(loadMockResponse(resource))));
    }

    public String loadMockResponse(Resource resource) {
        try {
            return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
