package project.shimozukuri.pastebin.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.shimozukuri.pastebin.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = """
            SELECT exists(
                          SELECT 1
                          FROM pastebin.users_notes
                          WHERE  user_id = :userId
                          AND note_id = :noteId)
           """, nativeQuery = true)
    boolean isNoteOwner(@Param("userId") Long userId,
                        @Param("noteId") Long noteId);
}
