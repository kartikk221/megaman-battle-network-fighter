public interface Manager {
    
    // Loads content into memory
    public void load(String name, String path);

    // Checks if some content is loaded
    public boolean isLoaded(String name);

    // Unloads content from memory
    public void unload(String name);
}
