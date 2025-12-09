import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BodyInfoController {

    private final BodyInfoRepository repository;
    private final List<BodyInfoObserver> observers = new ArrayList<>();

    public BodyInfoController(BodyInfoRepository repository) {
        this.repository = repository;
    }

    public void addObserver(BodyInfoObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BodyInfoObserver observer) {
        observers.remove(observer);
    }

    public void save(String username, BodyInfo bodyInfo) {
        repository.save(username, bodyInfo);
        observers.forEach(observer -> observer.onBodyInfoChanged(username, bodyInfo));
    }

    public Optional<BodyInfo> fetch(String username) {
        return repository.fetch(username);
    }
}
