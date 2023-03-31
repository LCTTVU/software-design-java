package softwaredesign;

/*
    When starting, Gradle might not download the dependencies for some reason
    Try the following:
    1. Toggle "Offline work" in Gradle (side panel or Global Gradle Settings)
    2. Re-sync the project by Invalidating Cache and Restarting IntelliJ
    3. Once synced, you can toggle the option again.

    Or you can just avoid this by using the Launcher run configuration.
    We have already included --module-path in its VM options to (potentially?) avoid this problem
 */



public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}
