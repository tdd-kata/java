package org.xpdojo.file.read;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xpdojo.file.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileReadService {

    private final Storage storage;

    /**
     * 서버에 저장되어 있는 디렉토리 목록을 조회한다.
     * <code>Files.isDirectory()</code>
     *
     * @return 디렉토리 목록
     * @throws IOException 디렉토리 목록 조회 실패
     */
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

    /**
     * 서버에 저장되어 있는 파일 목록을 조회한다.
     * <code>!Files.isDirectory()</code>
     *
     * @param id 디렉토리 ID
     * @return 파일 목록
     * @throws IOException 파일 목록 조회 실패
     */
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
}
