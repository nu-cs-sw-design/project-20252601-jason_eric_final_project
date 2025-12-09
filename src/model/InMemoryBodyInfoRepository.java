import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBodyInfoRepository implements BodyInfoRepository {

    private final Map<String, BodyInfo> store = new HashMap<>();

    @Override
    public synchronized void save(String username, BodyInfo bodyInfo) {
        store.put(username, bodyInfo);
    }

    @Override
    public synchronized Optional<BodyInfo> fetch(String username) {
        return Optional.ofNullable(store.get(username));
    }
}
