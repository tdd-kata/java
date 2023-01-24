package org.xpdojo.file.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class Storage {

    @Value("${file.upload.dir}")
    private String uploadDirectory;

    private int directoryId;

    public String getUploadDirectory() {
        return uploadDirectory;
    }

    public String getDirectory() {
        return uploadDirectory + directoryId;
    }

    public int increaseDirectoryId() {
        return ++directoryId;
    }

    @PostConstruct
    public void init() throws IOException {
        log.info("Memory init");
        try (Stream<Path> stream = Files.list(Paths.get(uploadDirectory))) {
            Set<String> directories = stream
                    // .filter(file -> !Files.isDirectory(file))
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(fileName -> !fileName.startsWith(".")) // like .DS_Store
                    .collect(Collectors.toSet());
            directoryId = directories.size();
        }
    }
}
