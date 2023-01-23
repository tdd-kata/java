package org.xpdojo.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        Set<String> paths = fileService.listFile();
        model.addAttribute("filePaths", paths);
        return "index";
    }

    @PostMapping(path = "/multipart", consumes = "multipart/form-data")
    public String multipart(
            @RequestParam("singleFile") MultipartFile singleFile,
            @RequestParam("multipleFiles") List<MultipartFile> multipleFiles
    ) throws IOException {
        // Spring MultipartFile
        if (singleFile.getSize() != 0) {
            log.info("### singleFile ###");
            fileService.printMultipartFile(singleFile);
            fileService.saveMultipartfile(singleFile);
        }
        if (multipleFiles.get(0).getSize() != 0) {
            log.info("### multipleFiles ###");
            for (MultipartFile multipleFile : multipleFiles) {
                fileService.printMultipartFile(multipleFile);
                fileService.saveMultipartfile(multipleFile);
            }
        }
        return "redirect:/";
    }

    @PostMapping(path = "/filepond", consumes = "multipart/form-data")
    public String filepond(
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        if (files.get(0).getSize() != 0) {
            log.info("### multipleFiles ###");
            for (MultipartFile multipleFile : files) {
                fileService.printMultipartFile(multipleFile);
                fileService.saveMultipartfile(multipleFile);
            }
        }
        return "redirect:/";
    }

    @PostMapping(path = "/servlet", consumes = "multipart/form-data")
    public String servlet(
            HttpServletRequest servletRequest
    ) throws IOException, ServletException {

        log.info("### servletRequest ###");
        log.info("{}", servletRequest.getContentType());
        // org.apache.catalina.core.ApplicationPart
        Collection<Part> parts = servletRequest.getParts();
        for (Part part : parts) {
            if (part.getSize() != 0) {
                fileService.printPart(part);
                fileService.savePart(part);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/images/{path}")
    @ResponseBody
    public Resource showImage(@PathVariable String path)
            throws MalformedURLException {
        // 이미지 저장 먼저
        return new UrlResource("file:" + fileService.getDirectory() + path);
    }

    @GetMapping("/download/{path:.+}")
    public ResponseEntity<Resource> download(@PathVariable String path)
            throws MalformedURLException {

        UrlResource resource = new UrlResource("file:" + fileService.getDirectory() + path);

        String encodedUploadFileName = UriUtils.encode(path, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
