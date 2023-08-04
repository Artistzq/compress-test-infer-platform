package com.kerbalogy.ctip.infer.od.controller;

import com.kerbalogy.ctip.infer.od.dto.ObjectDetectArgs;
import com.kerbalogy.ctip.infer.od.service.ObjectDetectService;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;

/**
 * @author yaozongqing@outlook.com
 * @description 物体检测
 * @date 2023-07-06
 **/
@RestController
@RequestMapping("/infer/od")
public class ObjectDetectionController {

    @Autowired
    ObjectDetectService objectDetectService;

    @PostMapping("/yolo")
    public ResponseEntity postImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam("args") String args
    ) {
        ObjectDetectArgs odArgs = JacksonUtil.from(args, ObjectDetectArgs.class);
        byte[] imageBytes;
        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            // Handle error if necessary
            throw new RuntimeException(e);
        }

        byte[] processedImageBytes = objectDetectService.requestOd(imageBytes, odArgs);

        // Set the response headers to indicate that the response is an image
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return the processed image as a ResponseEntity with appropriate headers
        return new ResponseEntity<>(processedImageBytes, headers, ResponseEntity.ok().build().getStatusCode());
    }
}
