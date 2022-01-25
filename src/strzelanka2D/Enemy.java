package strzelanka2D;

import java.util.Random;

/**
 * Ta klasa reprezentuje wrogów do ustrzelenia.
 * @author Piotr Pieni¹¿ek
 */
public class Enemy extends Object implements Commons
{
	private int diameter;	// Œrednica do wyznaczania kolizji
	// Pozycja centrum dla wyznaczania kolizji
	//private double center_x;
	//private double center_y;
	private int shot_cooldown = 0;
	
	/**
	 * Ten konstruktor inicjalizuje podstawowe parametry dla obiektu wroga.
	 * Odczytuje obraz, bierze z niego œrednicê (zak³adamy, ¿e wczytany obraz jest ko³em) i
	 * ustawia wroga w losowym miejscu nie bli¿ej ni¿ wartoœæ w interfejsie Commons
	 * od œrodka gracza podanego w argumentach.
	 * @param player_cent_x Pozycja X œrodka obiektu gracza.
	 * @param player_cent_y Pozycja Y œrodka obiektu gracza.
	 */
	public Enemy(int player_cent_x, int player_cent_y, boolean is_special)
	{
		setImageIcon(ENEMY_IMG);	// Wczytujemy domyœlny sprite.
		diameter = getImageIcon().getImage().getWidth(null);	// Pobieramy szerokoœæ sprite'a
		mov_speed = ENEMY_SPEED;
		is_special = false;
		
		Random generator = new Random();
		
		// Wspó³rzêdzna x
		do
			pos_x = generator.nextInt(BOARD_WIDTH);
		while (pos_x > player_cent_x - ENEMY_SPAWN_DISTANCE && pos_x < player_cent_x + ENEMY_SPAWN_DISTANCE);
		
		if (pos_x < 0)
			pos_x = 0;
		
		if (pos_x > BOARD_WIDTH - diameter)
			pos_x = BOARD_WIDTH - diameter;
		
		// Wspó³rzêdzna y
		do
			pos_y = generator.nextInt(BOARD_HEIGHT);
		while (pos_y > player_cent_y - ENEMY_SPAWN_DISTANCE && pos_y < player_cent_y + ENEMY_SPAWN_DISTANCE);
		
		if (pos_y < 0)
			pos_y = 0;
		
		if (pos_y > BOARD_HEIGHT - diameter)
			pos_y = BOARD_HEIGHT - diameter;
	}
	
	/**
	 * Ta metoda zmienia pozycjê obiektu zgodnie z pozycj¹ œrodka gracza.
	 * Obiekt wroga zbli¿a siê do gracza.
	 * @param player_cent_x Pozycja X œrodka obiektu gracza.
	 * @param player_cent_y Pozycja Y œrodka obiektu gracza.
	 */
	public void move(int player_cent_x, int player_cent_y)
	{
		// Wspó³rzêdna x
		if (player_cent_x > pos_x + diameter / 2)
			pos_x += mov_speed;
		else
			pos_x -= mov_speed;
		
		// Wspó³rzêdna y
		if (player_cent_y > pos_y + diameter / 2)
			pos_y += mov_speed;
		else
			pos_y -= mov_speed;
	}
	
	/**
	 * Ta metoda ustala czy obiekt koliduje siê z ko³em podanym jako parametry.
	 * @param x Pozycja X œrodka drugiego ko³a.
	 * @param y Pozycja Y œrodka drugiego ko³a.
	 * @param diameter Œrednica drugiego ko³a.
	 * @return true, jeœli dystans pomiêdzy œrodkami kó³ jest krótszy od sumy promieni kó³.
	 */
	public boolean isCollided(int x, int y, int diameter)
	{
		double center_x = pos_x + diameter / 2;
		double center_y = pos_y + diameter / 2;
		// Jeœli odleg³oœæ pomiêdzy œrodkami ko³a jest krótsza od sumy promieni, ko³a siê koliduj¹
		if ((center_x - x) * (center_x - x) + (center_y - y) * (center_y - y) < (this.diameter / 2 + diameter / 2) * (this.diameter / 2 + diameter / 2))
			return true;
		else
			return false;
	}
	
	public boolean canShoot()
	{
		if (shot_cooldown == 0)
			return true;
		
		shot_cooldown--;
		return false;
	}
	
	/**
	 * Ta metoda odczytuje koordynat X œrodka obiektu.
	 * @return Koordynat X œrodka obiektu.
	 */
	public int getCenterX() { return (int) Math.round(pos_x + diameter / 2);}
	
	/**
	 * Ta metoda odczytuje koordynat Y œrodka obiektu.
	 * @return Koordynat Y œrodka obiektu.
	 */
	public int getCenterY() { return (int) Math.round(pos_y + diameter / 2);}
	
	/**
	 * Ta metoda tworzy nowy strza³ wroga jeœli mo¿e w kierunku podanym jako argumenty.
	 * @param player_cent_x Pozycja X œrodka obiektu gracza.
	 * @param player_cent_y Pozycja Y œrodka obiektu gracza.
	 */
	public Shot shoot(int player_cent_x, int player_cent_y)
	{
		shot_cooldown = ENEMY_SHOT_DELAY;
		return new Shot((int) Math.round(pos_x + diameter / 2), (int) Math.round(pos_y + diameter / 2), player_cent_x, player_cent_y, ENEMY_SHOT_SPEED, ENEMY_SHOT_IMG);
	}
}
