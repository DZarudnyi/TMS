package layron.tms.service.attachment;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
            throws DbxException, FileTooBigException {
        if (file.getSize() > 157286400L) { //150mb
            throw new FileTooBigException("File size should be less than 150mb!");
        }

        taskRepository.findTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No task with id = " + taskId));

        Attachment attachment = new Attachment();
        Task task = new Task();
        task.setId(taskId);
        attachment.setTask(task);
        attachment.setFilename(file.getOriginalFilename());

        try {
            //This way of uploading allows for files less than 150mb
            FileMetadata uploadMetadata = dropboxClient
                    .files()
                    .uploadBuilder("/TMS/" + taskId + "/" + file.getOriginalFilename())
                    .uploadAndFinish(file.getInputStream());
            attachment.setUploadDate(LocalDateTime.now());
            attachment.setDropboxFileId(uploadMetadata.getId());

            Optional<Attachment> attachmentFromDb =
                    attachmentRepository.getAttachmentByDropboxFileId(attachment.getDropboxFileId());
            //checking if file is duplicate - already saved into db
            //potentially could count checksum instead?
            if (attachmentFromDb.isEmpty()) {
                attachmentRepository.save(attachment);
            } else {
                attachment.setId(attachmentFromDb.get().getId());
            }
            return attachmentMapper.toDto(attachment);
        } catch (Exception ex) {
            //TODO: add a message
            String attachmentPath = attachment.getFilePath() + "/" + attachment.getFilename();//TODO: Check correctness of pathing
            //TODO: create abstract class for such checks, according to clean architecture
            if (!dropboxClient
                    .files()
                    .searchV2(attachmentPath)
                    .getMatches()
                    .isEmpty()
            ) {
                dropboxClient
                        .files()
                        .deleteV2(attachmentPath);
            }
        }
        return new AttachmentDto();
    }

    @Override
    public List<AttachmentDto> getAttachmentsForTask(
            Long taskId
    ) throws DbxException {
        //As an option, it might be better to get list of files from dropbox, not from db
        //This might be a cheaper way, however if I use soft delete, then this approach won't work
        //Getting tasks from db is also much easier

        //Should I simply return the list, or download files?
        //Probably return a list and add download as a separate method

        return attachmentRepository.getAttachmentByTaskId(taskId).stream()
                .map(attachmentMapper::toDto)
                .toList();

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
//        return new ArrayList<>();
    }
}
