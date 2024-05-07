package codesquad.springcafe.database.file;

import codesquad.springcafe.model.UploadFile;
import java.util.List;
import java.util.Optional;

public interface FileDatabase {
    UploadFile add(UploadFile uploadFile);

    List<UploadFile> findAll(Long articleId);

    Optional<UploadFile> findBy(Long id);
}
