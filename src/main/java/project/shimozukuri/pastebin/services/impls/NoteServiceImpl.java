package project.shimozukuri.pastebin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shimozukuri.pastebin.dtos.note.NoteDto;
import project.shimozukuri.pastebin.entities.Note;
import project.shimozukuri.pastebin.entities.User;
import project.shimozukuri.pastebin.exeptions.ResourceNotFoundException;
import project.shimozukuri.pastebin.mappers.NoteMapper;
import project.shimozukuri.pastebin.repositories.NoteRepository;
import project.shimozukuri.pastebin.services.NoteService;
import project.shimozukuri.pastebin.utils.MinioUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final MinioUtil minioUtil;
    private final UserServiceImpl userService;
    private final NoteMapper noteMapper;

    @Override
    @Transactional
    public NoteDto getById(Long noteId, Long userId) {
        userService.getById(userId);
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Запись не найдена"));

        NoteDto noteDto = noteMapper.toDto(note);
        noteDto.setContent(minioUtil.getNote(note.getTitle()));

        return noteDto;
    }

    @Override
    @Transactional
    public NoteDto create(NoteDto noteDto, Long userId) {
        User user = userService.getById(userId);
        if (noteRepository.findByTitle(noteDto.getTitle()).isPresent()) {
            throw new IllegalStateException("Статья с таким названием уже существует");
        }

        minioUtil.uploadNote(noteDto);
        Note note = noteMapper.toEntity(noteDto);
        user.addNote(note);
        noteRepository.save(note);

        return noteDto;
    }

    @Override
    @Transactional
    public NoteDto update(NoteDto noteDto, Long noteId, Long userId) {
        userService.getById(userId);
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Запись не найдена"));

        note.setTitle(noteDto.getTitle());

        minioUtil.uploadNote(noteDto);
        noteRepository.save(note);

        return noteDto;
    }

    @Override
    @Transactional
    public void deleteById(Long noteId, Long userId) {
        userService.getById(userId);
        Note deletedNote = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Запись не найдена"));

        minioUtil.deleteNote(deletedNote.getTitle());
        noteRepository.deleteById(noteId);
    }

    @Override
    @Transactional
    public List<NoteDto> getAllByUserId(Long userId) {
        userService.getById(userId);
        List<Note> noteList = noteRepository.findAllByUserId(userId);

        if (noteList.isEmpty()) {
            throw new ResourceNotFoundException("Записи не найдены");
        }

        return noteList.stream().map(entity ->
                new NoteDto(entity.getTitle(), minioUtil.getNote(entity.getTitle()))).collect(Collectors.toList());
    }
}
