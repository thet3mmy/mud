package page.rightshift.mud.world;

public interface WorldBuilder {
    World build();

    static WorldBuilder newJsonWorldBuilder(String fileName) {
        return new JsonWorldBuilder(fileName);
    }
}
