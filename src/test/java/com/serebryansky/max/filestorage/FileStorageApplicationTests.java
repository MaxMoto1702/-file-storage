package com.serebryansky.max.filestorage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
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

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetContents() throws Exception {
        this.mockMvc.perform(get("/content").accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("content_get"));
    }

    @Test
    public void testGetContent() throws Exception {
        this.mockMvc.perform(get("/content/{id}", 1).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("content_get_item", pathParameters(
                        parameterWithName("id").description("Content ID")
                )));
    }

    @Test
    public void testGetContentStream() throws Exception {
        this.mockMvc.perform(get("/content/{id}/stream", 1).accept(APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7}))
                .andDo(document("content_get_stream", pathParameters(
                        parameterWithName("id").description("Content ID")
                )));
    }

    @Test
    public void testPostContent() throws Exception {
        this.mockMvc.perform(post("/content").accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content("{\"name\":\"file.xml\",\"mimeType\":\"application/xml\"}"))
                .andExpect(status().isOk())
                .andDo(document("content_post", requestFields(
                        fieldWithPath("name").type(STRING).description("Content name"),
                        fieldWithPath("mimeType").type(STRING).description("Content MIME type")
                ), responseFields(
                        fieldWithPath("id").type(NUMBER).description("Content ID"),
                        fieldWithPath("name").type(STRING).description("Content name"),
                        fieldWithPath("mimeType").type(STRING).description("Content MIME type"),
                        fieldWithPath("size").type(NUMBER).description("Content size in bytes")
                )));
    }

    @Test
    public void testPutContent() throws Exception {
        this.mockMvc
                .perform(put("/content/{id}", 1)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"file.xml\",\"mimeType\":\"application/xml\"}")
                )
                .andExpect(status().isOk())
                .andDo(document("content_put", pathParameters(
                        parameterWithName("id").description("Content ID")
                ), requestFields(
                        fieldWithPath("name").type(STRING).description("Content name").optional(),
                        fieldWithPath("mimeType").type(STRING).description("Content MIME type").optional()
                ), responseFields(
                        fieldWithPath("id").type(NUMBER).description("Content ID"),
                        fieldWithPath("name").type(STRING).description("Content name"),
                        fieldWithPath("mimeType").type(STRING).description("Content MIME type"),
                        fieldWithPath("size").type(NUMBER).description("Content size in bytes")
                )));
    }

    @Test
    public void testPutContentStream() throws Exception {
        this.mockMvc
                .perform(put("/content/{id}/stream", 1)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_OCTET_STREAM)
                        .content("<person><name>Max</name><sex>male</sex></person>".getBytes())
                )
                .andExpect(status().isOk())
                .andDo(document("content_put_stream", pathParameters(
                        parameterWithName("id").description("Content ID")
                ), responseFields(
                        fieldWithPath("id").type(NUMBER).description("Content ID"),
                        fieldWithPath("name").type(STRING).description("Content name"),
                        fieldWithPath("mimeType").type(STRING).description("Content MIME type"),
                        fieldWithPath("size").type(NUMBER).description("Content size in bytes")
                )));
    }

    @Test
    public void testDeleteContent() throws Exception {
        this.mockMvc
                .perform(delete("/content/{id}", 1))
                .andExpect(status().isNoContent())
                .andDo(document("content_delete", pathParameters(
                        parameterWithName("id").description("Content ID")
                )));
    }
}
