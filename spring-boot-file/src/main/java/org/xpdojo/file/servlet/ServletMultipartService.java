package org.xpdojo.file.servlet;

import com.trivago.triava.util.UnitFormatter;
import com.trivago.triava.util.UnitSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.xpdojo.file.storage.Storage;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServletMultipartService {

    private final Storage storage;

    /**
     * Servlet multipart/form-data 로그
     *
     * @param part multipart/form-data
     */
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
     * Servlet multipart/form-data 저장
     *
     * @param parts multipart/form-data
     * @throws IOException 파일 저장 실패
     */
    public void savePart(Collection<Part> parts) throws IOException {
        storage.increaseDirectoryId();
        String directory = storage.getDirectory();

        for (Part part : parts) {
            if (part.getSize() != 0) {
                printPart(part);
                Files.createDirectories(Paths.get(directory));
                log.info("directory {}", directory);

                String fullPath = directory + "/" + part.getSubmittedFileName();
                log.info("fullPath {}", fullPath);

                part.write(fullPath);
            }
        }
    }

}
