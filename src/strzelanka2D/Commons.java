package strzelanka2D;

/**
 * Ten interfejs zawiera zmienne sta³e wykorzystywane przez ró¿ne
 * klasy podczas dzia³ania programu.
 * @author Piotr Pieni¹¿ek
 */
public interface Commons
{
	// Wymiary okna
	public static final int BOARD_WIDTH = 960;
    public static final int BOARD_HEIGHT = 720;
    // Opóienie dla w¹tku animatora
    public static final int DELAY = 17;
    // Parametry gracza
    public static final int START_BOMBS = 3;
    public static final int PLAYER_SHOT_SPEED = 15;
    public static final int PLAYER_SHOT_COUNT = 7;
    public static final int PLAYER_SHOT_DELAY = 10;
    public static final String PLAYER_IMG = "src/img/Player.png";
    public static final String PLAYER_SHOT_IMG = "src/img/shot.png";
    // Parametry wrogów
    public static final int MERCY_DELAY = 30;
    public static final int ENEMY_SHOT_DELAY = 30;
    public static final int ENEMY_SPAWN_DISTANCE = 200;
    public static final int ENEMY_SPAWN_CHANCE = 60;
    public static final int ENEMY_COUNT = 5;
    public static final int ENEMY_SHOT_SPEED = 5;
    public static final int ENEMY_SPEED = 1;
    public static final String ENEMY_IMG = "src/img/enemy.png";
    public static final String ENEMY_SPECIAL_IMG = "src/img/enemy_special.png";
    public static final String ENEMY_SHOT_IMG = "src/img/shot2.png";
}
