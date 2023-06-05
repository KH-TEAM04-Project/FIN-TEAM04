package com.kh.team4.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import com.kh.team4.dto.FileUploadResDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final MemberRepository userRepository;

    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileUploadResDTO upload(MultipartFile multipartFile, String atk) throws IOException {
        System.out.println(atk + "실행ing");
        Authentication authentication = tokenProvider.getAuthentication(atk);
        Long mno = userRepository.findByMid2(authentication.getName());
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(mno, uploadFile, atk);
    }


    private FileUploadResDTO upload(Long mno, File uploadFile, String atk) {
        String fileName = atk + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        //사용자의 프로필을 등록하는 것이기때문에, Member 도메인에 setProfile을 해주는 코드.
        Member user = userRepository.findById(mno).get();
        user.setProfilePhoto(uploadImageUrl);

        //FileUploadResponse DTO로 반환해준다.
        return new FileUploadResDTO(fileName, uploadImageUrl);
        //return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }


}