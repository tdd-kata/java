package com.example.demo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ChunkFileController {

    private final ChunkFileService chunkFileService;

    @GetMapping("/chunk")
    public String chunkUploadPage() {
        return "chunk";
    }

    /**
     * @see <a href="https://velog.io/@haerong22/%EC%98%81%EC%83%81-%EC%8A%A4%ED%8A%B8%EB%A6%AC%EB%B0%8D-2.-%ED%8C%8C%EC%9D%BC-%EB%B6%84%ED%95%A0-%EC%97%85%EB%A1%9C%EB%93%9C">파일 분할 업로드</a>
     */
    @ResponseBody
    @PostMapping("/chunk/upload")
    public ResponseEntity<String> chunkUpload(
            @RequestParam("chunk") MultipartFile file,
            @RequestParam("chunkNumber") int chunkNumber,
            @RequestParam("totalChunks") int totalChunks
    ) throws IOException {
        return chunkFileService.chunkUpload(file, chunkNumber, totalChunks) ?
                // 200 OK
                ResponseEntity.ok("File uploaded successfully") :
                // 206 Partial Content
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }
}
