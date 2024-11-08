package com.udemy.star_d_link.global.common.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.udemy.star_d_link.global.common.dto.PreSignedUrlResponseDto;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public PreSignedUrlResponseDto getPreSignedUrl(String dir, String fileName) {
        if (dir != null && !dir.isEmpty()) {
            fileName = createPath(dir, fileName);
        }

        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(
            fileName);

        return generatePreSignedUrl(generatePresignedUrlRequest);
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String fileName) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + 180000));

        generatePresignedUrlRequest.addRequestParameter(
            Headers.S3_CANNED_ACL,
            CannedAccessControlList.PublicRead.toString());

        return generatePresignedUrlRequest;
    }

    private String createFileId() {
        return UUID.randomUUID().toString();
    }

    private String createPath(String prefix, String fileName) {
        String fileId = createFileId();
        return String.format("%s/%s", prefix, fileId + fileName);
    }

    private PreSignedUrlResponseDto generatePreSignedUrl(
        GeneratePresignedUrlRequest generatePresignedUrlRequest) {
        String preSignedUrl;
        String imageSaveUrl;
        preSignedUrl = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
        imageSaveUrl = amazonS3Client.getUrl(bucket, generatePresignedUrlRequest.getKey())
            .toString();

        return PreSignedUrlResponseDto.of(preSignedUrl, imageSaveUrl);
    }
}