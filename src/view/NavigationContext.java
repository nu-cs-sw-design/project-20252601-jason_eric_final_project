public interface NavigationContext {
    void showScreen(String name);
    boolean ensureLoggedIn();
    String getCurrentUser();
    void setCurrentUser(String currentUser);
}
