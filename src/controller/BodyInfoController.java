import java.util.Optional;

public class BodyInfoController {

    private final BodyInfoRepository repository;

    public BodyInfoController(BodyInfoRepository repository) {
        this.repository = repository;
    }

    public void save(String username, BodyInfo bodyInfo) {
        repository.save(username, bodyInfo);
    }

    public Optional<BodyInfo> fetch(String username) {
        return repository.fetch(username);
    }
}
