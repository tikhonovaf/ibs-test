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
@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 4567)
public class DepartmentServiceTest {

//    @RegisterExtension
//    public static WireMockExtension wireMockRule = WireMockExtension.newInstance()
//        .options(wireMockConfig().port(4567))
//        .build();

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    //    @Autowired
//    DepartmentClient departmentClient;
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
//        departmentService.loadDepartments().get();
        //  Начальная загрузка
        WireMock.stubFor(get(urlEqualTo("/departments"))
                .willReturn(okJson(loadMockResponse(departmentsResourceInit))));
        departmentService.loadDepartments().get();
        Assertions.assertEquals(10, departmentRepository.findAll().size());

        //  Загрузка с изменением
        WireMock.stubFor(get(urlEqualTo("/departments"))
                .willReturn(okJson(loadMockResponse(departmentsResourceUpdated))));
        departmentService.loadDepartments().get();;
        Assertions.assertTrue(departmentRepository.findById(1789l).orElse(null).getCode().contains("updated"));
        //  Загрузка с удалением
        WireMock.stubFor(get(urlEqualTo("/departments"))
                .willReturn(okJson(loadMockResponse(departmentsResourceDeleted))));
        departmentService.loadDepartments().get();;
        Assertions.assertEquals(7, departmentRepository.findAll().size());

    }

    @Test
    public void loadProjects() throws IOException, ExecutionException, InterruptedException {
        var ps = projectService.loadProjects();

    }

    public String loadMockResponse(Resource resource) {
        try {
            return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
