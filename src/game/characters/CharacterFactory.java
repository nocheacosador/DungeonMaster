package game.characters;

public class CharacterFactory {
    public static final String[] HEROES = {
        "Wizard", "Knight"
    };

    public static final String[] MONSTERS = {
        "Big Zombie", "Tiny Zombie", "Skelet"
    };

    public static final String[] BOSSES = {
        "Demon"
    };

    public static Character createCharacter(String name) {
        switch (name) {
            // heroes:
            case "Wizard":      return new Wizard();
            case "Knight":      return new Knight();
            // bad guyz:
            case "Big Zombie":  return new BigZombie();
            case "Tiny Zombie": return new TinyZombie();
            case "Skelet":      return new Skelet();
            case "Demon":       return new Demon();
            default:            return null;
        }
    }
}