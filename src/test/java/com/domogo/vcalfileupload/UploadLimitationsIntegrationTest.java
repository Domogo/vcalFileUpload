package com.domogo.vcalfileupload;

import com.domogo.vcalfileupload.repository.FileRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
    locations = "classpath:application-test.properties"
)
public class UploadLimitationsIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FileRepository fileRepository;

    @Test
    public void fileUpload_whenActiveUploadLimit_thenStatus429() {
    }

    @Test
    public void fileUpload_whenFileSizeTooBig_thenStatus413() {

    }

    @Test
    public void fileUpload_whenFileWithThatNameCurrentlyUploading_thenStatus409() {

    }

}