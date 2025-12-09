import java.util.Optional;

public interface BodyInfoRepository {
    void save(String username, BodyInfo bodyInfo);
    Optional<BodyInfo> fetch(String username);
}
