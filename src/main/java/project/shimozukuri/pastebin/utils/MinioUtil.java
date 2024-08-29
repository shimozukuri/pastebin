package project.shimozukuri.pastebin.utils;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import project.shimozukuri.pastebin.dtos.note.NoteDto;
import project.shimozukuri.pastebin.exeptions.MinioException;
import project.shimozukuri.pastebin.utils.properties.MinioProperties;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MinioUtil {
    public final MinioClient minioClient;
    public final MinioProperties minioProperties;

    public void uploadNote(NoteDto noteDto) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new MinioException("Ошибка загрузки записи: " + e.getMessage());
        }

        if(noteDto.getTitle().isEmpty()) {
            throw new MinioException("У записи должно быть название");
        } else if(noteDto.getContent().isEmpty()) {
            throw new MinioException("Запись не может быть пустой");
        }

        InputStream inputStream = new ByteArrayInputStream(noteDto.getContent().getBytes(StandardCharsets.UTF_8));

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(noteDto.getTitle())
                            .stream(inputStream, noteDto.getContent().length(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioException("Ошибка загрузки записи: " + e.getMessage());
        }
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    public void deleteNote(String name) {
        try {
            RemoveObjectArgs removedNote = RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .build();

            minioClient.removeObject(removedNote);
        } catch (Exception e) {
            throw new MinioException("Ошибка удаления записи: " + e.getMessage());
        }
    }

    public String getNote(String name) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .build());

            return getStringFromInputStream(inputStream);
        } catch (Exception e) {
            throw new MinioException("Ошибка выгрузки записи: " + e.getMessage());
        }
    }

    private String getStringFromInputStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines().collect(Collectors.joining());
    }
}
