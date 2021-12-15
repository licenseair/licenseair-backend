package com.licenseair.dashboard.controller;

import com.licenseair.backend.commons.model.QueryResponse;
import com.licenseair.backend.commons.util.HttpRequestException;
import com.licenseair.dashboard.config.OSSConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

import org.springframework.web.multipart.MultipartFile;


/**
 * Created by programschool.com
 */
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {
  private static String endpoint = OSSConfig.imageEndpoint;
  private static String accessKeyId = "LTAI4G274FQ3bs8THAhy31gZ";
  private static String accessKeySecret = "FbGXjGRQGFGsVF1apRlNTFDn0qeCpV";

  private static String bucketName = "programschool-assets";
  private static String newFilename;

  @PostMapping("")
  public QueryResponse create(@RequestParam MultipartFile file) throws IOException, HttpRequestException {
    /*
     * Constructs a client instance with your account for accessing OSS
     */
    OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    OSSObject object = null;
    try {

      /**
       * Note that there are two ways of uploading an object to your bucket, the one
       * by specifying an input stream as content source, the other by specifying a file.
       */

      /*
       * Upload an object to your bucket from an input stream
       */
      logger.info("Uploading a new object to OSS from an input stream\n");
      String[] sp = file.getContentType().split("/");
      String imageType = sp[0].toLowerCase();
      String extension = sp[1].toLowerCase();
      if (!imageType.equals("image") && !extension.equals("jpg") && !extension.equals("jpeg") && extension.equals("png")) {
        throw new HttpRequestException(HttpStatus.BAD_REQUEST.value(), "请上传支持的类型（jpg/jpeg、png）文件");
      }
      newFilename = String.format("%s.%s", UUID.randomUUID().toString(), extension);
      // System.out.println(newFilename);
      client.putObject(bucketName, newFilename, new ByteArrayInputStream(file.getBytes()));

      /*
       * Download an object from your bucket
       */
      logger.info("Downloading an object");
      object = client.getObject(new GetObjectRequest(bucketName, newFilename));
      logger.info("Content-Type: "  + object.getObjectMetadata().getContentType());

    } catch (OSSException oe) {
      logger.info("Caught an OSSException, which means your request made it to OSS, "
        + "but was rejected with an error response for some reason.");
      logger.info("Error Message: " + oe.getErrorMessage());
      logger.info("Error Code:       " + oe.getErrorCode());
      logger.info("Request ID:      " + oe.getRequestId());
      logger.info("Host ID:           " + oe.getHostId());
    } catch (ClientException ce) {
      logger.info("Caught an ClientException, which means the client encountered "
        + "a serious internal problem while trying to communicate with OSS, "
        + "such as not being able to access the network.");
      logger.info("Error Message: " + ce.getMessage());
    } finally {
      /*
       * Do not forget to shut down the client finally to release all allocated resources.
       */
      client.shutdown();
    }
    return new QueryResponse(Map.of("filename", newFilename));
  }

}
