package org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.java;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see org.springframework.beans.factory.config.BeanDefinitionVisitor
 */
class SearchFileVisitorTest {

    @Test
    void sut_search_file_visitor() throws IOException {
        Path searchFile = Paths.get(this.getClass().getSimpleName() + ".java");
        Path startingDirectory = Paths.get("src", "test");
        File file = startingDirectory.toFile();
        String path = file.getAbsolutePath();
        Path absolutePath = Path.of(path);

        assertThat(path).endsWith("src" + File.separator + "test");

        System.out.println(absolutePath);
        SearchFileVisitor searchFileVisitor =
                new SearchFileVisitor(searchFile.toString(), absolutePath);

        Path result = Files.walkFileTree(absolutePath, searchFileVisitor);

        assertThat(result).hasToString(path);
    }

}
