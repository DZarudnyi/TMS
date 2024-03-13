package layron.tms.service.attachment;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.mapper.AttachmentMapper;
import layron.tms.model.Attachment;
import layron.tms.model.Task;
import layron.tms.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final DbxClientV2 dropboxClient;
    private final AttachmentMapper attachmentMapper;

    @Override
    public AttachmentDto upload(Long taskId, MultipartFile file) throws IOException, DbxException {
        Attachment attachment = new Attachment();
        Task task = new Task();
        task.setId(taskId);
        attachment.setTask(task);
        attachment.setFilename(file.getName());

        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
        FileMetadata uploadMetadata = dropboxClient.files().uploadBuilder("myfilepath").uploadAndFinish(inputStream);
        inputStream.close();

        attachment.setUploadDate(LocalDateTime.now());
        attachment.setDropboxFileId(uploadMetadata.getId());
        attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    public List<MultipartFile> getAttachmentsForTask(Long taskId) throws DbxException {
        List<Attachment> attachmentsForTask = attachmentRepository.getAttachmentByTaskId(taskId);
        List<MultipartFile> downloadedFiles = new ArrayList<>();
        for (Attachment attachement : attachmentsForTask) {
            DbxDownloader<FileMetadata> downloadedFile = dropboxClient.files().download(attachement.getDropboxFileId());//listFolder(fileId)
            InputStream inputStream = downloadedFile.getInputStream();
        }
        return null;
    }
}
