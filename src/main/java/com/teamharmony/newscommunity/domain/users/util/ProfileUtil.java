package com.teamharmony.newscommunity.domain.users.util;

import com.teamharmony.newscommunity.exception.InvalidRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.entity.ContentType.*;

public class ProfileUtil {
    public static Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    public static void isImage(MultipartFile file) {
        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType())
                   .contains(file.getContentType()))
            throw InvalidRequestException.builder()
                                         .message("파일이 이미지가 아닙니다.")
                                         .invalidValue("파일 유형: " + file.getContentType())
                                         .code("U402")
                                         .build();
    }
}