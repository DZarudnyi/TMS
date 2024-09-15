package layron.tms.controller;

import java.util.List;
import layron.tms.dto.label.CreateLabelRequestDto;
import layron.tms.dto.label.LabelDto;
import layron.tms.dto.label.UpdateLabelRequestDto;
import layron.tms.service.label.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/labels")
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDto createLabel(@RequestBody CreateLabelRequestDto requestDto) {
        return labelService.createLabel(requestDto);
    }

    @GetMapping
    public List<LabelDto> getAllLabels() {
        return labelService.getAllLabels();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LabelDto updateLabel(
            @PathVariable Long id,
            @RequestBody UpdateLabelRequestDto requestDto
    ) {
        return labelService.updateLabel(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
