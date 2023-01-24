package org.xpdojo.file.spring;

import com.trivago.triava.util.UnitFormatter;
import com.trivago.triava.util.UnitSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xpdojo.file.storage.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringMultipartService {

    private final Storage storage;

    /**
     * Spring multipart/form-data 저장
     *
     * @param files multiple multipart/form-data
     * @throws IOException 파일 저장 실패
     */
    public void saveMultipart(List<MultipartFile> files)
            throws IOException {

        // if (singleFile.getSize() != 0) {
        //     log.info("### singleFile ###");
        //     DIRECTORY_ID++;
        //     printMultipartFile(singleFile);
        //     saveMultipartfile(singleFile);
        // }
        if (files.get(0).getSize() != 0) {
            log.info("### multipleFiles ###");
            int id = storage.increaseDirectoryId();
            for (MultipartFile file : files) {
                printMultipartFile(file);
                saveMultipartFile(String.valueOf(id), file);
            }
        }
    }

    /**
     * Spring multipart/form-data 로그
     *
     * @param multipartFile multipart/form-data
     */
    public void printMultipartFile(MultipartFile multipartFile) {
        log.info("{}", multipartFile.getOriginalFilename());
        log.debug("{}", multipartFile.getName());
        log.debug("{}", multipartFile.getContentType());
        log.debug("{}", multipartFile.getSize() + " Bytes");
        log.debug("{}", FileUtils.byteCountToDisplaySize(multipartFile.getSize()));
        log.debug("{}", UnitFormatter.formatAsUnit(multipartFile.getSize(), UnitSystem.SI, "B"));
        log.debug("{}", UnitFormatter.formatAsUnit(multipartFile.getSize(), UnitSystem.IEC, "B"));
        log.debug("{}", UnitFormatter.formatAsUnit(multipartFile.getSize(), UnitSystem.JEDEC, "B"));
    }

    /**
     * Spring multipart/form-data 저장
     *
     * @param id            디렉토리 ID
     * @param multipartFile multipart/form-data
     * @throws IOException 파일 저장 실패
     */
    public void saveMultipartFile(String id, MultipartFile multipartFile) throws IOException {
        String directory = storage.getUploadDirectory() + id;
        Files.createDirectories(Paths.get(directory));

        String fullPath = directory + "/" + multipartFile.getOriginalFilename();
        log.info("fullPath {}", fullPath);
        multipartFile.transferTo(Paths.get(fullPath));
    }

    /**
     * 기존 디렉토리에 파일 추가
     *
     * @param id    디렉토리 ID
     * @param files 추가할 파일
     * @throws IOException 파일 저장 실패
     */
    public void addMultipart(
            String id,
            List<MultipartFile> files)
            throws IOException {

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("files not found");
        }

        if (files.get(0).getSize() != 0) {
            log.info("### multipleFiles ###");
            for (MultipartFile file : files) {
                printMultipartFile(file);
                saveMultipartFile(id, file);
            }
        }
    }

    /**
     * 파일 삭제
     *
     * @param id       디렉토리 ID
     * @param filename 삭제할 파일명
     */
    public void removeMultipart(String id, String filename) {
        String fullPath = storage.getUploadDirectory() + id + "/" + filename;
        log.info("fullPath {}", fullPath);
        try {
            Files.delete(Paths.get(fullPath));
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        }
    }
}
