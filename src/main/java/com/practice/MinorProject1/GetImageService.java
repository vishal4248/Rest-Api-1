package com.practice.MinorProject1;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class GetImageService {

    private final RestTemplate restTemplate;
    private static final String IMAGE_FOLDER = "C:/prajapat/getImages";
    public GetImageService() {
        restTemplate = new RestTemplate();
    }


    public byte[] getImageFromUrl(int id) {
        String url = "https://picsum.photos/id/"+ id +"/200/300";
        return restTemplate.getForObject(url,byte[].class);
    }



    public void getMultipleImages(String ids, HttpServletResponse httpServletResponse) {

        try {

            new File(IMAGE_FOLDER).mkdirs();

            String []id_arr = ids.split(",");
            List<Integer> id_list = new ArrayList<>();
            for(String val : id_arr) {
                id_list.add(Integer.parseInt(val));
            }

//            System.out.print("id_list = ");
//            for (int val : id_list) {
//                System.out.print(val+", ");
//            }
//            System.out.println();

            for(Integer currentId : id_list) {

                byte[] img = restTemplate.getForObject("https://picsum.photos/id/" + currentId + "/200/300", byte[].class);

                File file = new File(IMAGE_FOLDER+"/"+currentId+".png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                assert img != null;
                fileOutputStream.write(img);

                fileOutputStream.close();
            }

            ZipOutputStream zipOutputStream = new ZipOutputStream(httpServletResponse.getOutputStream());
            for(Integer currentId: id_list) {

                FileSystemResource fileSystemResource = new FileSystemResource(IMAGE_FOLDER + "/" + currentId + ".png");
                ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(fileSystemResource.getFilename()));

                zipEntry.setSize(fileSystemResource.contentLength());
                zipOutputStream.putNextEntry(zipEntry);

                StreamUtils.copy(fileSystemResource.getInputStream(),zipOutputStream);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
            httpServletResponse.setStatus(200);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
