package test;

import app.logger.UploadService;

import java.io.IOException;

public class UploadTest {
    public static void main(String[] args) {
        UploadService service= new UploadService();
        String url= null;
        try {
            url = service.upload("f:\\10.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("args = [" + url + "]");
    }
}
