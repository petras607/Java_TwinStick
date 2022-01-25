package strzelanka2D;

import java.util.Random;

/**
 * Ta klasa reprezentuje wrogów do ustrzelenia. Istniej¹ dwa rodzaje wrogów do ustrzelenia.
 * Specjalny zachowuje siê identycznie jak domyœlny, ale ustrzelenie go przywraca grazcowi
 * pocz¹tkowy zapas bomb.
 * @author Piotr Pieni¹¿ek
 */
public class Enemy extends Object implements Commons
{
	private int diameter;	// Œrednica do wyznaczania kolizji
	// Pozycja centrum dla wyznaczania kolizji
	private double center_x;
	private double center_y;
	// Strza³
	public Shot shot;
	private boolean just_spawned;
	private int shot_delay;
	private boolean is_special;
	
	/**
	 * Ten konstruktor inicjalizuje podstawowe parametry dla obiektu wroga.
	 * Odczytuje obraz, bierze z niego œrednicê (zak³adamy, ¿e wczytany obraz jest ko³em)
	 * i tworzymy dla niego pusty strza³.
	 */
	public Enemy()
	{
		setImageIcon(ENEMY_IMG);	// Wczytujemy domyœlny sprite.
		diameter = getImageIcon().getImage().getWidth(null);	// Pobieramy szerokoœæ sprite'a
		mov_speed = ENEMY_SPEED;
		is_special = false;
		
		// Strza³
		shot = new Shot();
		shot.setVisible(false);
	}
	
	/**
	 * Ta metoda ustawia wroga w losowym miejscu nie bli¿ej ni¿ wartoœæ w interfejsie Commons
	 * od œrodka gracza podanego w argumentach.
	 * @param player_cent_x Pozycja X œrodka obiektu gracza.
	 * @param player_cent_y Pozycja Y œrodka obiektu gracza.
	 */
	public void spawn(int player_cent_x, int player_cent_y)
	{
		Random generator = new Random();
		
		// Wspó³rzêdzna x
		do
			pos_x = generator.nextInt(BOARD_WIDTH);
		while (pos_x > player_cent_x - ENEMY_SPAWN_DISTANCE && pos_x < player_cent_x + ENEMY_SPAWN_DISTANCE);
		
		if (pos_x < 0)
			pos_x = 0;
		
		if (pos_x > BOARD_WIDTH - diameter)
			pos_x = BOARD_WIDTH - diameter;
		
		center_x = pos_x + diameter / 2;
		
		// Wspó³rzêdzna y
		do
			pos_y = generator.nextInt(BOARD_HEIGHT);
		while (pos_y > player_cent_y - ENEMY_SPAWN_DISTANCE && pos_y < player_cent_y + ENEMY_SPAWN_DISTANCE);
		
		if (pos_y < 0)
			pos_y = 0;
		
		if (pos_y > BOARD_HEIGHT - diameter)
			pos_y = BOARD_HEIGHT - diameter;
		
		center_y = pos_y + diameter / 2;
		
		this.setVisible(true);
		
		just_spawned = true;
		shot_delay = 0;
	}
	
	/**
	 * Ta metoda mówi czy dany wróg jest specjalnym wrogiem.
	 * @return true, jeœli wróg jest specjalny.
	 */
	public boolean getSpecial() { return is_special; }
	
	/**
	 * Ta metoda zmienia rodzaj danego wroga i zmienia odpowiednio obraz reprezentuj¹cy go.
	 * @param special Nowy rodzaj. true oznacza specjalnego wroga.
	 */
	public void setSpecial(boolean special)
	{
		is_special = special;
		
		if (is_special)
			setImageIcon(ENEMY_SPECIAL_IMG);
		else
			setImageIcon(ENEMY_IMG);
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
		if (player_cent_x > center_x)
			pos_x += mov_speed;
		else
			pos_x -= mov_speed;
		
		center_x = pos_x + diameter / 2;
		
		// Wspó³rzêdna y
		if (player_cent_y > center_y)
			pos_y += mov_speed;
		else
			pos_y -= mov_speed;
		
		center_y = pos_y + diameter / 2;
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
		// Jeœli odleg³oœæ pomiêdzy œrodkami ko³a jest krótsza od sumy promieni, ko³a siê koliduj¹
		if ((center_x - x) * (center_x - x) + (center_y - y) * (center_y - y) < (this.diameter / 2 + diameter / 2) * (this.diameter / 2 + diameter / 2))
			return true;
		else
			return false;
	}
	
	/**
	 * Ta metoda tworzy nowy strza³ wroga jeœli mo¿e w kierunku podanym jako argumenty.
	 * @param player_cent_x Pozycja X œrodka obiektu gracza.
	 * @param player_cent_y Pozycja Y œrodka obiektu gracza.
	 */
	public void shoot(int player_cent_x, int player_cent_y)
	{
		if (!just_spawned)
			shot = new Shot((int) Math.round(center_x), (int) Math.round(center_y), player_cent_x, player_cent_y, ENEMY_SHOT_SPEED, ENEMY_SHOT_IMG);
		else
		{
			if (shot_delay < ENEMY_SHOT_DELAY)
				shot_delay += 1;
			else
				just_spawned = false;
		}
	}
}
