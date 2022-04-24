package org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.java;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author whiteship
 */
public class SearchFileVisitor implements FileVisitor<Path> {

    Logger logger = LoggerFactory.getLogger(SearchFileVisitor.class);

    private final String fileToSearch;
    private final Path startingDirectory;

    public SearchFileVisitor(String fileToSearch, Path startingDirectory) {
        this.fileToSearch = fileToSearch;
        this.startingDirectory = startingDirectory;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        // logger.info("preVisitDirectory: " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // logger.info("visitFile: {}", file);
        if (fileToSearch.equals(file.getFileName().toString())) {
            logger.info("found {} in {}", file.getFileName(), file.getParent());

            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
        logger.error("visitFileFailed", e);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        // logger.info("postVisitDirectory: " + dir);
        if (Files.isSameFile(startingDirectory, dir)) {
            System.out.println("Not found");
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }
}
