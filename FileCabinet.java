import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileCabinet implements Cabinet{
    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }


    @Override
    public Optional<Folder> findFolderByName(String name) {
        return streamAllFolders()
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return streamAllFolders()
                .filter(folder -> folder.getSize().equals(size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) streamAllFolders().count();
    }



    // helper methods
    private Stream<Folder> streamAllFolders() {
            return folders.stream()
                    .flatMap(this::streamFolder);
    }

    private Stream<Folder> streamFolder(Folder folder){
        if (folder instanceof MultiFolder){
            MultiFolder multiFolder = (MultiFolder) folder;
            return Stream.concat(
                    Stream.of(folder),
                    multiFolder.getFolders().stream()
                            .flatMap(this::streamFolder));
        } else
            return Stream.of(folder);
    }

}
