package com.domogo.vcalfileupload.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.domogo.vcalfileupload.model.FileRecord;

public class WriteFileUtil {

    public static void copyInputStreamToFile( InputStream in, File file, FileRecord fr ) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            long uploaded = 0;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
                uploaded += len;
                fr.setUploaded(uploaded);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}