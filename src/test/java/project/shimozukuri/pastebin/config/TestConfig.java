package project.shimozukuri.pastebin.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.shimozukuri.pastebin.mappers.NoteMapper;
import project.shimozukuri.pastebin.mappers.UserMapper;
import project.shimozukuri.pastebin.repositories.NoteRepository;
import project.shimozukuri.pastebin.repositories.RoleRepository;
import project.shimozukuri.pastebin.repositories.UserRepository;
import project.shimozukuri.pastebin.services.impls.AuthServiceImpl;
import project.shimozukuri.pastebin.services.impls.NoteServiceImpl;
import project.shimozukuri.pastebin.services.impls.RoleServiceImpl;
import project.shimozukuri.pastebin.services.impls.UserServiceImpl;
import project.shimozukuri.pastebin.utils.JwtTokenUtil;
import project.shimozukuri.pastebin.utils.MinioUtil;
import project.shimozukuri.pastebin.utils.properties.JwtProperties;
import project.shimozukuri.pastebin.utils.properties.MinioProperties;

import static org.mockito.Mockito.mock;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserServiceImpl userService(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        return new UserServiceImpl(
                userRepository,
                roleService(roleRepository),
                testPasswordEncoder(),
                userMapper()
        );
    }

    @Bean
    @Primary
    public AuthServiceImpl authService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager
    ) {
        return new AuthServiceImpl(
                userService(userRepository, roleRepository),
                jwtTokenUtil(),
                authenticationManager
        );
    }

    @Bean
    @Primary
    public NoteServiceImpl noteService(
            NoteRepository noteRepository,
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        return new NoteServiceImpl(
                noteRepository,
                minioUtil(),
                userService(userRepository, roleRepository),
                noteMapper()
        );
    }

    @Bean
    @Primary
    public RoleServiceImpl roleService(RoleRepository roleRepository) {
        return new RoleServiceImpl(
                roleRepository
        );
    }

    @Bean
    @Primary
    public MinioUtil minioUtil() {
        return new MinioUtil(
                minioClient(),
                minioProperties()
        );
    }

    @Bean
    @Primary
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(
                jwtProperties()
        );
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return mock(AuthenticationManager.class);
    }

    @Bean
    public UserRepository userRepository() {
        return mock(UserRepository.class);
    }

    @Bean
    public RoleRepository roleRepository() {
        return mock(RoleRepository.class);
    }

    @Bean
    public NoteRepository noteRepository() {
        return mock(NoteRepository.class);
    }

    @Bean
    public UserMapper userMapper() {
        return mock(UserMapper.class);
    }

    @Bean
    public NoteMapper noteMapper() {
        return mock(NoteMapper.class);
    }

    @Bean
    public MinioClient minioClient() {
        return mock(MinioClient.class);
    }

    @Bean
    public MinioProperties minioProperties() {
        MinioProperties properties = new MinioProperties();
        properties.setBucket("notes");
        return properties;
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf"
        );
        return jwtProperties;
    }
}
