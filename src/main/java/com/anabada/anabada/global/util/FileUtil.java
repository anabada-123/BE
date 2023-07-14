package com.anabada.anabada.global.util;

import com.anabada.anabada.global.exception.FileException;
import com.anabada.anabada.global.exception.type.FileErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FileUtil {

    private final ResourceLoader loader;

    //TODO: EC2 AWS 배포 한후 IP 적어야함.
    private String serverIp = "";


    public List<String> saveFileList(String userId, List<MultipartFile> files) {

        String path;

        Resource resource = loader.getResource("classpath:static/img");
        try {
            path = resource.getFile().getAbsolutePath();
        }catch (IOException e){
            throw new FileException(FileErrorCode.FILE_ERROR);
        }


        List<String> fileNameList = new ArrayList<>();
        File directirFile = new File(path + "\\" + userId);
        for (MultipartFile file : files) {
            fileNameList.add(serverIp+saveFile(file, directirFile, userId));
        }
        return fileNameList;
    }

    public String saveFileOne(String userId, MultipartFile file) {

        String path;

        Resource resource = loader.getResource("classpath:static/img");
        try {
            path = resource.getFile().getAbsolutePath();
        }catch (IOException e){
            throw new FileException(FileErrorCode.FILE_ERROR);
        }


        File directirFile = new File(path + "\\" + userId);
        return serverIp+saveFile(file, directirFile, userId);
    }

    private String saveFile(MultipartFile file, File directirFile,String userId)  {

        String path;
        Resource resource = loader.getResource("classpath:static/img");
        try {
            path = resource.getFile().getAbsolutePath();
        }catch (IOException e){
            throw new FileException(FileErrorCode.FILE_ERROR);
        }

        //존재 하지 않을경우
        if (!directirFile.exists()) {
            // 디렉터리 생성에 실패했을 경우
            if (!directirFile.mkdirs()) {
                System.out.println("file: was not successful");
            }
        }
        String contentType = file.getContentType(); //확장자 추출
        //확장자 존재 하지 않을 경우 TODO: 에러 처리 해야함.
        if (ObjectUtils.isEmpty(contentType)) {
            throw new FileException(FileErrorCode.FILE_NOT_EXTENSION);
        }
        if (!(
                contentType.equals("image/jpg") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/png"))
        ) {
            //확장자 이상할 경우 TODO: 에러 처리 해야함.
            throw new FileException(FileErrorCode.FILE_ERROR_EXTENSION);
        }else{
            contentType = switch (contentType){
                case "image/jpg" -> ".jpg";
                case "image/jpeg" -> ".jpeg";
                case "image/png" -> ".png";
                default -> "";//TODO: 에러 처리
            };
        }
        //중복 방지로 나노초로 방지.
        String new_file_name = System.nanoTime() + contentType;
        File downloadFile = new File(path+"\\"+userId+"\\" + new_file_name);

        try {
            file.transferTo(downloadFile);
        }catch (IOException e){
            //TODO: 에러 처리 저장 안되는 에러 처리.
            System.out.println(e);
        }
        return userId+"\\"+new_file_name;
    }



}
