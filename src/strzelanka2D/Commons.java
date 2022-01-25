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
    public static final int ENEMY_SHOT_DELAY = 100;
    public static final int ENEMY_SPAWN_DISTANCE = 200;
    public static final int ENEMY_SPAWN_CHANCE = 60;
    public static final int ENEMY_COUNT = 10;
    public static final int ENEMY_SHOT_SPEED = 5;
    public static final int ENEMY_SPEED = 1;
    public static final String ENEMY_IMG = "src/img/enemy.png";
    public static final String ENEMY_SHOT_IMG = "src/img/shot2.png";
    // Parametry przedmiotów
    public static final String PICKUP_IMG = "src/img/pickup.png";
    public static final String BOMB_IMG = "src/img/bomb.png";
    public static final int LIFE_SPAN = 10;
    public static final double PICKUP_START_SPEED = 0.5;
    public static final double PICKUP_SLOWDOWN_RATE = 0.2;
    public static final int PICKUP_SCORE_GAIN = 10;
    public static final int ITEM_SPAWN_COUNT = 3;
}
