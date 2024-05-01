package project.shimozukuri.pastebin.services;

import project.shimozukuri.pastebin.dtos.note.NoteDto;

import java.util.List;

public interface NoteService {
    NoteDto getById(Long noteId, Long userId);

    NoteDto create(NoteDto noteDto, Long userId);

    NoteDto update(NoteDto note, Long id, Long userId);

    void deleteById(Long noteId, Long userId);

    List<NoteDto> getAllByUserId(Long userId);
}
