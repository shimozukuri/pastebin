package project.shimozukuri.pastebin.mappers;

public interface Mappable<E, D> {

    D toDto(E entity);

    E toEntity(D dto);
}
