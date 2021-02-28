package com.afonso.cursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author afonso.gomes
 */
@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String pLocalPath) {
        try {
            LOG.info("Iniciando Upload!");
            File oFlile = new File(pLocalPath);
            s3client.putObject((new PutObjectRequest(bucketName, "teste.png", oFlile)));
             LOG.info("Upload finalizado!");
        } catch (AmazonServiceException e) {
            LOG.info("AmazonServiceException: " + e.getErrorMessage());
            LOG.info("Satus code: " + e.getErrorCode());
        
        } catch(AmazonClientException e){
            LOG.info("AmazonClientException: " + e.getMessage());
        }
    }
}
