package com.serebryansky.max.filestorage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FileStorageApplicationTests {


    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("build/generated-snippets");

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    private FieldDescriptor[] content = new FieldDescriptor[]{
            fieldWithPath("_links").description("HAL"),
            fieldWithPath("id").type(NUMBER).description("Content ID"),
            fieldWithPath("name").type(STRING).description("Content name"),
            fieldWithPath("type").type(STRING).description("Content MIME type"),
            fieldWithPath("size").type(NUMBER).description("Content size in bytes"),
            fieldWithPath("createDate").type(VARIES).description("Content create date"),
            fieldWithPath("lastModifiedDate").type(VARIES).description("Content last modified date")
    };

    @Test
    public void testGetContents() throws Exception {
        this.mockMvc.perform(get("/api/contents"))
                .andExpect(status().isOk())
                .andDo(document("content_get", responseFields(
                        fieldWithPath("_links").description("HAL"),
                        fieldWithPath("page").description("Page information"),
                        fieldWithPath("_embedded.contents[]").description("An array of books")
                ).andWithPrefix("_embedded.contents[].", content)));
    }

    @Test
    public void testGetContent() throws Exception {
        this.mockMvc.perform(get("/api/contents/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("content_get_item", pathParameters(
                        parameterWithName("id").description("Content ID")
                ), responseFields(content)));
    }

    @Test
    public void testGetContentStream() throws Exception {
        this.mockMvc
                .perform(get("/api/contents/{id}/stream", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("test"))
                .andDo(document("content_get_stream", pathParameters(
                        parameterWithName("id").description("Content ID")
                )));
    }

    @Test
    public void testPostContent() throws Exception {
        this.mockMvc.perform(post("/api/contents").content("{\"name\":\"file.xml\",\"type\":\"application/xml\"}"))
                .andExpect(status().isCreated())
                .andDo(document("content_post", requestFields(
                        fieldWithPath("name").type(STRING).description("Content name"),
                        fieldWithPath("type").type(STRING).description("Content MIME type")
//                ), responseFields(content)));
                )));
    }

    @Test
    public void testPutContent() throws Exception {
        this.mockMvc
                .perform(put("/api/contents/{id}", 1)
                        .content("{\"name\":\"file.xml\",\"type\":\"application/xml\"}")
                )
//                .andExpect(status().isOk())
                .andExpect(status().isNoContent())
                .andDo(document("content_put", pathParameters(
                        parameterWithName("id").description("Content ID")
                ), requestFields(
                        fieldWithPath("name").type(STRING).description("Content name").optional(),
                        fieldWithPath("type").type(STRING).description("Content MIME type").optional()
//                ), responseFields(content)));
                )));
    }

    @Test
    public void testPutContentStream() throws Exception {
        this.mockMvc
                .perform(put("/api/contents/{id}/stream", 1)
                        .content("<person><name>Max</name><sex>male</sex></person>".getBytes())
                )
                .andExpect(status().isOk())
                .andDo(document("content_put_stream"));
    }

    @Test
    public void testDeleteContent() throws Exception {
        this.mockMvc
                .perform(delete("/api/contents/{id}", 2))
                .andExpect(status().isNoContent())
                .andDo(document("content_delete", pathParameters(
                        parameterWithName("id").description("Content ID")
                )));
    }
}
