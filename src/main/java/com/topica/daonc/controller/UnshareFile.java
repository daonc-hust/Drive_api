package com.topica.daonc.controller;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.topica.daonc.ApiApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class UnshareFile {
    String permissionID;
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, ApiApplication.getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

    public UnshareFile() throws IOException, GeneralSecurityException {
    }

    @GetMapping("/unshare_file")
    public String unshareFile(@RequestParam String id, @RequestParam String idUser) throws IOException {
        JsonBatchCallback<Void> callback = new JsonBatchCallback<Void>() {
            @Override
            public void onFailure(GoogleJsonError googleJsonError, HttpHeaders httpHeaders) throws IOException {
            }

            @Override
            public void onSuccess(Void aVoid, HttpHeaders httpHeaders) throws IOException {
            }
        };
        BatchRequest batch = service.batch();
        service.permissions().delete(id, idUser).queue(batch, callback);
        batch.execute();
        return "";
    }
}
