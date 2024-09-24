package layron.tms.service.attachment;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.core.v2.users.FullAccount;
import jakarta.transaction.Transactional;
import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.exception.FileTooBigException;
import layron.tms.exception.TaskNotFoundException;
import layron.tms.mapper.AttachmentMapper;
import layron.tms.model.Attachment;
import layron.tms.model.Task;
import layron.tms.repository.AttachmentRepository;
import layron.tms.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final DbxClientV2 dropboxClient;
    private final AttachmentMapper attachmentMapper;

    @Override
    @Transactional
    public AttachmentDto upload(Long taskId, MultipartFile file)
            throws IOException, DbxException, FileTooBigException {
        if (file.getSize() > 157286400L) { //150mb
            throw new FileTooBigException("File size should be less than 150mb!");
        }

        //need to use global exception handler here
        taskRepository.findTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No task with id = " + taskId));

        Attachment attachment = new Attachment();
        Task task = new Task();
        task.setId(taskId);
        attachment.setTask(task);
        attachment.setFilename(file.getOriginalFilename());



        //FullAccount account = dropboxClient.users().getCurrentAccount();
        //System.out.println(account.getName().getDisplayName());

        //should add check for file in db/dropbox
        //if this file is already in db - getting exception on save

        //If file already exists - update date in db, upload file to dropbox (there is automated versioning of files)

        //This way of uploading allows for files less than 150mb
        FileMetadata uploadMetadata = dropboxClient
                .files()
                .uploadBuilder("/TMS/" + taskId + "/" + file.getOriginalFilename())
                .uploadAndFinish(file.getInputStream());
        attachment.setUploadDate(LocalDateTime.now());
        attachment.setDropboxFileId(uploadMetadata.getId());

        attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);//getting exception here;dto converts correctly, getting exception on return

    }

    @Override
    public List<ResponseEntity<InputStreamResource>> getAttachmentsForTask(
            Long taskId
    ) throws DbxException {
        //dropboxClient.files().listFolder(path) //не лізти зайвий раз в базу, а просто взяти в дропбоксі
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
