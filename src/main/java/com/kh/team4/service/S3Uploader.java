package com.kh.team4.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import com.kh.team4.dto.FileUploadResDTO;
import com.kh.team4.entity.Member;
import com.kh.team4.jwt.TokenProvider;
import com.kh.team4.repository.MemberRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public FileUploadResDTO upload(MultipartFile multipartFile, String profile, String atk) throws IOException {
        System.out.println("multipartFile = profile == " + profile);
        System.out.println("atk" + atk);
        Long mno = userRepository.findByMid2(tokenProvider.getAuthentication(atk).getName());
        System.out.println("여기까지 실행, mno값은? " + mno);
        File uploadFile = convert(multipartFile) // profile 넘어가는중
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(mno, uploadFile, atk);
    }

    private FileUploadResDTO upload(Long mno, File uploadFile, String filePath) {
        System.out.println("uploadFile 값은? " + uploadFile);
        String fileName = filePath + uploadFile.getName(); // S3에 저장된 파일 이름
        System.out.println("fileName: " + fileName);
        String uploadImageUrl = putS3(uploadFile, fileName); // S3로 업로드
        System.out.println("uploadImageUrl = " + uploadImageUrl);
        removeNewFile(uploadFile);

        //사용자의 프로필을 등록하는 것이기때문에, Member 도메인에 setProfile을 해주는 코드.
        Member user = userRepository.findById(mno).get();
        System.out.println("해당 mno값? " + user.getMno());
        user.setProfilephoto(uploadImageUrl);
        userRepository.save(user);
        System.out.println("해당 유저의 프로필이 들어가는지 확인" + user.getProfilephoto());
        String ProfilePhoto = user.getProfilephoto();
        //FileUploadResponse DTO로 반환해준다.
        return new FileUploadResDTO(fileName, uploadImageUrl, ProfilePhoto);
    }


    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        System.out.println("uploadFile: " + uploadFile);
        System.out.println("fileName: " + fileName);
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
        System.out.println("실행중입니다요" + file);
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        System.out.println("convertFile: " + convertFile);
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                System.out.println("fos: " + fos);
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


}