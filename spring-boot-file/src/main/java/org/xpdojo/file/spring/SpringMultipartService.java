package org.xpdojo.file.spring;

import com.trivago.triava.util.UnitFormatter;
import com.trivago.triava.util.UnitSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xpdojo.file.storage.Storage;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringMultipartService {

    private final Storage storage;

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

    public void saveMultipartFile(String id, MultipartFile multipartFile) throws IOException {
        String directory = storage.getUploadDirectory() + id;
        Files.createDirectories(Paths.get(directory));

        String fullPath = directory + "/" + multipartFile.getOriginalFilename();
        log.info("fullPath {}", fullPath);
        multipartFile.transferTo(Paths.get(fullPath));
    }

    public void printPart(Part part) {
        log.info("{}", part.getSubmittedFileName());
        log.debug("{}", part.getName());
        log.debug("{}", part.getContentType());
        // InputStream inputStream = part.getInputStream();
        // String text =
        //         new BufferedReader(
        //                 new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        //                 .lines()
        //                 .collect(Collectors.joining("\n"));
        // log.info(text);
        Collection<String> headerNames = part.getHeaderNames();
        for (String headerName : headerNames) {
            log.debug("{}: {}", headerName, part.getHeader(headerName));
        }
        log.debug("{}", part.getSize() + " Bytes");
        log.debug("{}", FileUtils.byteCountToDisplaySize(part.getSize()));
        log.debug("{}", UnitFormatter.formatAsUnit(part.getSize(), UnitSystem.SI, "B"));
        log.debug("{}", UnitFormatter.formatAsUnit(part.getSize(), UnitSystem.IEC, "B"));
        log.debug("{}", UnitFormatter.formatAsUnit(part.getSize(), UnitSystem.JEDEC, "B"));
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
}
