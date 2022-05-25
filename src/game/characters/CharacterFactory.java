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
            case "Wizard":      return new Wizard(name);
            case "Knight":      return new Knight(name);
            // bad guyz:
            case "Big Zombie":  return new BigZombie(name);
            case "Tiny Zombie": return new TinyZombie(name);
            case "Skelet":      return new Skelet(name);
            case "Demon":       return new Demon(name);
            default:            return null;
        }
    }
}