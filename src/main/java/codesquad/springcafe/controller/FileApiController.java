package codesquad.springcafe.controller;

import codesquad.springcafe.model.UploadFile;
import codesquad.springcafe.service.FileStoreService;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/api/file")
public class FileApiController {
    private final FileStoreService fileStoreService;

    public FileApiController(FileStoreService fileStoreService) {
        this.fileStoreService = fileStoreService;
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long fileId) throws MalformedURLException {
        UploadFile uploadFile = fileStoreService.findFileById(fileId);
        String uploadFileName = uploadFile.getUploadFileName();
        String storeFileName = uploadFile.getStoreFileName();

        UrlResource resource = new UrlResource("file:" + fileStoreService.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
