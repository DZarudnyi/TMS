package layron.tms.service.attachment;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.core.v2.users.FullAccount;
import jakarta.transaction.Transactional;
import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.mapper.AttachmentMapper;
import layron.tms.model.Attachment;
import layron.tms.model.Task;
import layron.tms.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
//@PropertySource("classpath:dropbox.properties")
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final DbxClientV2 dropboxClient;
    private final AttachmentMapper attachmentMapper;
//    @Value("${dropbox.auth.token}")
//    String token;

    @Override
    @Transactional
    public AttachmentDto upload(Long taskId, MultipartFile file) throws IOException, DbxException {
        Attachment attachment = new Attachment();
        Task task = new Task();
        task.setId(taskId);
        attachment.setTask(task);
        attachment.setFilename(file.getName());

        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());

//        DbxRequestConfig config = DbxRequestConfig.newBuilder("TMS/1.0").build();
//
//        DbxClientV2 dropboxClient = new DbxClientV2(config, token);

        FullAccount account = dropboxClient.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        FileMetadata uploadMetadata = dropboxClient

                .files()
                .uploadBuilder("/TMS")
                .uploadAndFinish(inputStream);
        inputStream.close();

        attachment.setUploadDate(LocalDateTime.now());
        attachment.setDropboxFileId(uploadMetadata.getId());
        attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    public List<ResponseEntity<InputStreamResource>> getAttachmentsForTask(
            Long taskId
    ) throws DbxException {
//        List<Attachment> attachmentsForTask = attachmentRepository.getAttachmentByTaskId(taskId);
//        List<ResponseEntity<InputStreamResource>> downloadedFiles = new ArrayList<>();
//        for (Attachment attachment : attachmentsForTask) {
//            DbxDownloader<FileMetadata> downloadedFile = dropboxClient
//                    .files()
//                    .download(attachment.getDropboxFileId());
//            MediaType contentType = MediaType.parseMediaType(downloadedFile.getContentType());
//            downloadedFiles.add(
//                    ResponseEntity.ok()
//                            .contentType(contentType)
//                            .body(new InputStreamResource(downloadedFile.getInputStream()))
//            );
//        }
//        return downloadedFiles;
        return new ArrayList<>();
    }
}
