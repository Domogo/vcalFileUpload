package com.domogo.vcalfileupload;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import com.domogo.vcalfileupload.model.FileRecord;
import com.domogo.vcalfileupload.repository.FileRepository;
import com.domogo.vcalfileupload.service.FileStorageService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


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

    @Autowired
    FileStorageService fileStorageService;

    @Test
    public void fileUpload_whenActiveUploadLimit_thenStatus429() throws Exception {
        // create a 100 active uploads mock
        for (int i = 0; i < 100; i++) {
            FileRecord fr = new FileRecord();
            Date date = new Date();
            long timestamp = date.getTime();
            fr.setId("file" + i + '-' + timestamp);
            fr.setName("file" + i);
            fr.setInProgress(true);
            fileRepository.save(fr);
        }

        // try to make an upload request, the upload should fail - 100 parallel uploads is the limit
        MockMultipartFile fakeFile = new MockMultipartFile("file", "test.zip", "application/zip", "somezip".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                        .file(fakeFile)
                        .header("XUpload-File", "NewFileName"))
                    .andExpect(status().is(429));
    }

    @Test
    public void fileUpload_whenFileSizeTooBig_thenStatus413() {

    }

    @Test
    public void fileUpload_whenFileWithThatNameCurrentlyUploading_thenStatus409() {

    }

}