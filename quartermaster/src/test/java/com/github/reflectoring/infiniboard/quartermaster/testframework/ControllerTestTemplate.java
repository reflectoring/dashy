package com.github.reflectoring.infiniboard.quartermaster.testframework;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.github.reflectoring.infiniboard.quartermaster.ControllerTestApp;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.hypermedia.JsonPathLinksSnippet;
import org.springframework.restdocs.hypermedia.JsonPathResponseFieldsSnippet;
import org.springframework.restdocs.hypermedia.LinkDescriptor;
import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ControllerTestApp.class)
@WebAppConfiguration
public abstract class ControllerTestTemplate {

  private MockMvc mvc;

  @Autowired private WebApplicationContext applicationContext;

  @Rule
  public JUnitRestDocumentation restDocumentation =
      new JUnitRestDocumentation("build/generated-snippets");

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    mvc =
        MockMvcBuilders.webAppContextSetup(applicationContext)
            .apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation))
            .build();
  }

  @SuppressWarnings("unchecked")
  protected <T> ConstrainedFields<T> fields(Class<T> clazz) {
    return new ConstrainedFields(clazz);
  }

  public static class ConstrainedFields<T> {

    private final ConstraintDescriptions constraintDescriptions;

    ConstrainedFields(Class<T> input) {
      this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    public FieldDescriptor withPath(String path) {
      return fieldWithPath(path)
          .attributes(
              key("constraints")
                  .value(
                      StringUtils.collectionToDelimitedString(
                          this.constraintDescriptions.descriptionsForProperty(path), ". ")));
    }
  }

  /**
   * Wraps the static document() method of RestDocs and configures it to pretty print request and
   * response JSON structures.
   */
  protected RestDocumentationResultHandler document(String identifier, Snippet... snippets) {
    return MockMvcRestDocumentation.document(
        identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), snippets);
  }

  protected MockMvc mvc() {
    return mvc;
  }

  protected JsonPathResponseFieldsSnippet responseFieldsInPath(
      String jsonPath, FieldDescriptor... fieldDescriptors) {
    return new JsonPathResponseFieldsSnippet(jsonPath, fieldDescriptors);
  }

  protected LinksSnippet linksInPath(String jsonPath, LinkDescriptor... linkDescriptors) {
    return new JsonPathLinksSnippet(jsonPath, linkDescriptors);
  }
}
