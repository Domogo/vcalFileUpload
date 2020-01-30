package com.domogo.vcalfileupload;

import com.domogo.vcalfileupload.property.StorageProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	StorageProperties.class
})
public class VcalFileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(VcalFileUploadApplication.class, args);
	}

}
