package com.practice.MinorProject1;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class UnzipService {

    public String unzippingOfFile(HttpServletRequest httpServletRequest) {

        try {
            Part part = httpServletRequest.getPart("file");

            System.out.println("Requested file = "+part.getSubmittedFileName());
            System.out.println("Requested content type = "+part.getContentType());
            System.out.println("Requested size = "+part.getSize());

            File outputFolder = new File("C:/prajapat");

            ZipInputStream zipInputStream = new ZipInputStream(part.getInputStream());
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            byte []current = new byte[1024];

            while(zipEntry != null){
                String file_name = zipEntry.getName();

                if(!zipEntry.isDirectory()){
                    File outputFile = new File(outputFolder + "/" + file_name);
                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

                    int len = 0;
                    while( (len = zipInputStream.read(current)) > 0 ){
                        fileOutputStream.write(current, 0, len);
                    }
                    fileOutputStream.close();
                } else{
                    new File(outputFolder + "/" + file_name).mkdirs();
                }
                zipInputStream.closeEntry();

                zipEntry = zipInputStream.getNextEntry();
            }
            return "File: "+part.getSubmittedFileName()+" unzipping is successfully done.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during unzipping.....";
        }
    }
}
