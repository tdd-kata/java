package org.xpdojo.file.read;

import com.trivago.triava.util.UnitFormatter;
import com.trivago.triava.util.UnitSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xpdojo.file.storage.Storage;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileReadService {

    private final Storage storage;

    public Set<String> listFile() throws IOException {

        // Set<String> paths = Stream.of(new File(directory).listFiles())
        //         .filter(file -> !file.isDirectory())
        //         .map(File::getName)
        //         .collect(Collectors.toSet());

        try (Stream<Path> stream = Files.list(Paths.get(storage.getUploadDirectory()))) {
            return stream
                    // .filter(file -> !Files.isDirectory(file))
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(fileName -> !fileName.startsWith(".")) // like .DS_Store
                    .collect(Collectors.toSet());
        } catch (NoSuchFileException noSuchFileException) {
            log.error("{}", noSuchFileException.getMessage());
            Path directories = Files.createDirectories(Paths.get(storage.getUploadDirectory()));
            File[] files = directories.toFile().listFiles();
            return Objects.requireNonNull(files).length == 0
                    ? Set.of()
                    : listFile();
        }
    }

    public Set<String> listFilesInDirectory(int id) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(storage.getUploadDirectory() + id))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(fileName -> !fileName.startsWith(".")) // like .DS_Store
                    .collect(Collectors.toSet());
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
        log.info("directory {}", storage.getUploadDirectory());

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
     * Servlet 파일 저장
     *
     * @param parts 파일
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
        if (files.get(0).getSize() != 0) {
            log.info("### multipleFiles ###");
            for (MultipartFile file : files) {
                printMultipartFile(file);
                saveMultipartFile(id, file);
            }
        }
    }
}
