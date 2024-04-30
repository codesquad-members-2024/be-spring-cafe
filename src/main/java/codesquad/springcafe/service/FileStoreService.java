package codesquad.springcafe.service;

import codesquad.springcafe.database.file.FileDatabase;
import codesquad.springcafe.exception.FileNotFoundException;
import codesquad.springcafe.model.UploadFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
public class FileStoreService {
    private final FileDatabase fileDatabase;
    @Value("${file.dir}")
    private String fileDir;

    public FileStoreService(FileDatabase fileDatabase) {
        this.fileDatabase = fileDatabase;
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public UploadFile storeFile(Long articleId, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        UploadFile uploadFile = new UploadFile(articleId, originalFilename, storeFileName);
        fileDatabase.add(uploadFile);
        return uploadFile;
    }

    public List<UploadFile> findFilesByArticleId(Long articleId) {
        return fileDatabase.findAll(articleId);
    }

    public UploadFile findFileById(Long id) {
        return fileDatabase.findBy(id).orElseThrow(FileNotFoundException::new);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
