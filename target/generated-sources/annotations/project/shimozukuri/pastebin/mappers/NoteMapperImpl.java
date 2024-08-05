package project.shimozukuri.pastebin.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import project.shimozukuri.pastebin.dtos.note.NoteDto;
import project.shimozukuri.pastebin.entities.Note;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-27T19:50:32+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public NoteDto toDto(Note entity) {
        if ( entity == null ) {
            return null;
        }

        NoteDto noteDto = new NoteDto();

        noteDto.setTitle( entity.getTitle() );

        return noteDto;
    }

    @Override
    public Note toEntity(NoteDto dto) {
        if ( dto == null ) {
            return null;
        }

        Note note = new Note();

        note.setTitle( dto.getTitle() );

        return note;
    }
}
