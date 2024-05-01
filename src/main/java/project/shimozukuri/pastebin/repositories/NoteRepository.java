package project.shimozukuri.pastebin.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.shimozukuri.pastebin.entities.Note;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    Optional<Note> findByTitle(String title);

    @Query(value = """
            SELECT * FROM pastebin.notes n
            JOIN pastebin.users_notes un ON un.note_id = n.id
            WHERE un.user_id = :userId
            """, nativeQuery = true)
    List<Note> findAllByUserId(@Param("userId") Long userId);
}
