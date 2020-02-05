package com.domogo.vcalfileupload;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
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
                        .header("XUpload-File", "NewFileName.zip"))
                    .andExpect(status().is(429));

    }


    @Test
    public void fileUpload_whenFileSizeTooBig_thenStatus413() throws Exception {
        // commons multipart resolver handles this correctly, but the test here returns
        // 200 as MockMvc does not work with it, couldn't find a working way to test this.

        // Mock sending a file larger than 50MB by putting a file of 80MB to input stream
        FileInputStream fis = new FileInputStream("./FileTooBig.zip");
        MockMultipartFile fakeFile = new MockMultipartFile("file", "test.zip", "application/zip", fis);

        // try to make an upload request, the upload should fail - 100 parallel uploads is the limit
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                        .file(fakeFile)
                        .header("XUpload-File", "NewFileName.zip"))
                    .andExpect(status().is(413));

    }


    @Test
    public void fileUpload_whenFileWithThatNameCurrentlyUploading_thenStatus409() throws Exception {

        // create a mock active upload, since it is in progress it should block
        // another upload with the same name from being uploaded

        String fileName = "testName.zip";

        FileRecord fr = new FileRecord();
        Date date = new Date();
        long timestamp = date.getTime();
        fr.setId(fileName + "-" + timestamp);
        fr.setName(fileName);
        fr.setInProgress(true);
        fileRepository.save(fr);

        MockMultipartFile fakeFile = new MockMultipartFile("file", "test.zip", "application/zip", "somezip".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/upload")
                        .file(fakeFile)
                        .header("XUpload-File", fileName))
                    .andExpect(status().is(409));

    }

}