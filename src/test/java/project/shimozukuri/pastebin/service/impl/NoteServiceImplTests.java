package project.shimozukuri.pastebin.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shimozukuri.pastebin.config.TestConfig;
import project.shimozukuri.pastebin.dtos.note.NoteDto;
import project.shimozukuri.pastebin.entities.Note;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.exeptions.ResourceNotFoundException;
import project.shimozukuri.pastebin.mappers.NoteMapper;
import project.shimozukuri.pastebin.repositories.NoteRepository;
import project.shimozukuri.pastebin.services.impls.NoteServiceImpl;
import project.shimozukuri.pastebin.services.impls.UserServiceImpl;
import project.shimozukuri.pastebin.utils.MinioUtil;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTests {

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private MinioUtil minioUtil;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private NoteMapper noteMapper;

    @Autowired
    private NoteServiceImpl noteService;

    @Test
    public void NoteServiceImpl_GetById_ReturnNoteDto() {
        Long noteId = 1L;
        Long userId = 1L;
        String title = "title";
        String content = "content";

        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(title);

        Note note = new Note();
        note.setTitle(title);

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));
        when(noteMapper.toDto(note)).thenReturn(noteDto);
        when(minioUtil.getNote(title)).thenReturn(content);

        NoteDto testNoteDto = noteService.getById(noteId, userId);

        verify(userService).getById(userId);
        verify(noteRepository).findById(noteId);
        verify(noteMapper).toDto(note);
        verify(minioUtil).getNote(title);

        assertEquals(content, testNoteDto.getContent());
    }

    @Test
    public void NoteServiceImpl_GetById_ReturnResourceNotFoundException() {
        Long noteId = 1L;
        Long userId = 1L;

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> noteService.getById(noteId, userId)
        );

        verify(userService).getById(userId);
        verify(noteRepository).findById(noteId);
    }

    @Test
    public void NoteServiceImpl_Create_ReturnNoteDto() {
        Long userId = 1L;
        String title = "title";
        String content = "content";

        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(title);
        noteDto.setContent(content);

        Note note = new Note();
        note.setTitle(title);

        User user = new User();
        user.setNotes(new ArrayList<>());

        when(userService.getById(userId)).thenReturn(user);
        when(noteRepository.findByTitle(title)).thenReturn(Optional.empty());
        when(noteMapper.toEntity(noteDto)).thenReturn(note);

        noteService.create(noteDto, userId);

        verify(userService).getById(userId);
        verify(noteRepository).findByTitle(title);
        verify(minioUtil).uploadNote(noteDto);
        verify(noteMapper).toEntity(noteDto);
        verify(noteRepository).save(note);
    }

    @Test
    public void NoteServiceImpl_Create_ReturnIllegalStateException() {
        Long userId = 1L;
        String title = "title";

        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(title);

        when(userService.getById(userId)).thenReturn(new User());
        when(noteRepository.findByTitle(title)).thenReturn(Optional.of(new Note()));

        assertThrows(
                IllegalStateException.class,
                () -> noteService.create(noteDto, userId)
        );

        verify(userService).getById(userId);
        verify(noteRepository).findByTitle(title);
    }

    @Test
    public void NoteServiceImpl_Update_ReturnNoteDto() {
        Long noteId = 1L;
        Long userId = 1L;
        String title = "title";
        String content = "content";

        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(title);
        noteDto.setContent(content);

        Note note = new Note();
        note.setTitle(title);

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        noteService.update(noteDto, noteId, userId);

        verify(userService).getById(userId);
        verify(noteRepository).findById(noteId);
        verify(minioUtil).uploadNote(noteDto);
        verify(noteRepository).save(note);
    }

    @Test
    public void NoteServiceImpl_DeleteById_ReturnVoid() {
        Long noteId = 1L;
        Long userId = 1L;
        String title = "title";

        Note note = new Note();
        note.setTitle(title);

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        noteService.deleteById(noteId, userId);

        verify(userService).getById(userId);
        verify(noteRepository).findById(noteId);
        verify(minioUtil).deleteNote(title);
        verify(noteRepository).deleteById(noteId);
    }

    @Test
    public void NoteServiceImpl_GetAllByUserId_ReturnListOfNoteDto() {
        Long userId = 1L;
        String title = "title";
        String content = "content";

        Note note = new Note();
        note.setTitle(title);

        List<Note> noteList = new LinkedList<>();
        noteList.add(note);

        when(noteRepository.findAllByUserId(userId)).thenReturn(noteList);
        when(minioUtil.getNote(title)).thenReturn(content);

        List<NoteDto> testNoteDtoList = noteService.getAllByUserId(userId);

        verify(userService).getById(userId);
        verify(noteRepository).findAllByUserId(userId);
        verify(minioUtil).getNote(title);

        assertEquals(content, testNoteDtoList.get(0).getContent());
    }

    @Test
    public void NoteServiceImpl_GetAllByUserId_ReturnResourceNotFoundException() {
        Long userId = 1L;

        when(noteRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(
                ResourceNotFoundException.class,
                () -> noteService.getAllByUserId(userId)
        );

        verify(userService).getById(userId);
        verify(noteRepository).findAllByUserId(userId);
    }
}
