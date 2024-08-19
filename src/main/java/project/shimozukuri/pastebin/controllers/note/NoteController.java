package project.shimozukuri.pastebin.controllers.note;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.shimozukuri.pastebin.dtos.note.NoteDto;
import project.shimozukuri.pastebin.services.NoteService;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
@RequiredArgsConstructor
@Tag(name = "Note controller", description = "Note API")
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/notes")
    public List<NoteDto> getAllByUserId(@PathVariable(value = "userId") Long id) {
        return noteService.getAllByUserId(id);
    }

    @PostMapping("/new")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public NoteDto create(
            @RequestBody NoteDto noteDto,
            @PathVariable(value = "userId") Long userId
    ) {
        return noteService.create(noteDto, userId);
    }

    @GetMapping("/note/{noteId}")
    public NoteDto getById(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "noteId") Long noteId
    ) {
        return noteService.getById(noteId, userId);
    }

    @PutMapping("/note/{noteId}")
    @PreAuthorize("@cse.canAccessNote(#noteId)")
    public NoteDto update(
            @RequestBody NoteDto noteDto,
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "noteId") Long noteId
    ) {
        return noteService.update(noteDto, noteId, userId);
    }

    @DeleteMapping("/note/{noteId}")
    @PreAuthorize("@cse.canAccessNote(#noteId)")
    public void deleteById(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "noteId") Long noteId
    ) {
        noteService.deleteById(noteId, userId);
    }
}
