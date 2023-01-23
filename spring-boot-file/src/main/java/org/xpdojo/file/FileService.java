package org.xpdojo.file;

import com.trivago.triava.util.UnitFormatter;
import com.trivago.triava.util.UnitSystem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileService {

    // private final String userHome = System.getProperty("user.home");
    @Value("${file.upload.dir}")
    private String directory;

    public Set<String> listFile() throws IOException {

        // Set<String> paths = Stream.of(new File(directory).listFiles())
        //         .filter(file -> !file.isDirectory())
        //         .map(File::getName)
        //         .collect(Collectors.toSet());

        try (Stream<Path> stream = Files.list(Paths.get(directory))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(fileName -> !fileName.startsWith(".")) // like .DS_Store
                    .collect(Collectors.toSet());
        }
    }

    public String getDirectory() {
        return directory;
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

    public void saveMultipartfile(MultipartFile multipartFile) throws IOException {
        Files.createDirectories(Paths.get(directory));
        log.info("directory {}", directory);

        String fullPath = directory + multipartFile.getOriginalFilename();
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

    public void savePart(Part part) throws IOException {
        Files.createDirectories(Paths.get(directory));
        log.info("directory {}", directory);

        String fullPath = directory + part.getSubmittedFileName();
        log.info("fullPath {}", fullPath);

        part.write(fullPath);
    }

}
