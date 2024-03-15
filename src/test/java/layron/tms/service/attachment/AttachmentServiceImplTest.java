package layron.tms.service.attachment;

import layron.tms.dto.attachment.AttachmentDto;
import layron.tms.mapper.AttachmentMapper;
import layron.tms.model.Attachment;
import layron.tms.model.Task;
import layron.tms.repository.AttachmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AttachmentServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
    private static final String DEFAULT_FILENAME = "File";
    private static final String DEFAULT_DROPBOX_ID = "a4ayc_80_OEAAAAAAAAAXw";

    private Attachment attachment;
    @Mock
    private AttachmentServiceImpl attachmentService;
    @Mock
    private AttachmentRepository attachmentRepository;
    @Mock
    private AttachmentMapper attachmentMapper;

    @BeforeEach
    public void setup() {
        attachment = new Attachment();
        attachment.setTask(getTask());
        attachment.setUploadDate(DEFAULT_DATE);
        attachment.setFilename(DEFAULT_FILENAME);
        attachment.setDropboxFileId(DEFAULT_DROPBOX_ID);
    }

    @Test
    void upload() {
    }

    @Test
    void getAttachmentsForTask() {
    }

    private Task getTask() {
        Task task = new Task();
        task.setId(DEFAULT_ID);
        return task;
    }

    private AttachmentDto getAttachmentDto() {
        return new AttachmentDto(
                DEFAULT_ID,
                DEFAULT_ID,
                DEFAULT_DROPBOX_ID,
                DEFAULT_FILENAME,
                DEFAULT_DATE
        );
    }
}