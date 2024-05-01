package project.shimozukuri.pastebin.mappers;

import org.mapstruct.Mapper;
import project.shimozukuri.pastebin.dtos.note.NoteDto;
import project.shimozukuri.pastebin.entities.Note;

@Mapper(componentModel = "spring")
public interface NoteMapper extends Mappable<Note, NoteDto> {
}
