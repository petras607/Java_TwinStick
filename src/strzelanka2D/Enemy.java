package strzelanka2D;

import java.util.Random;

/**
 * Ta klasa reprezentuje wrog�w do ustrzelenia. Istniej� dwa rodzaje wrog�w do ustrzelenia.
 * Specjalny zachowuje si� identycznie jak domy�lny, ale ustrzelenie go przywraca grazcowi
 * pocz�tkowy zapas bomb.
 * @author Piotr Pieni��ek
 */
public class Enemy extends Object implements Commons
{
	private int diameter;	// �rednica do wyznaczania kolizji
	// Pozycja centrum dla wyznaczania kolizji
	private double center_x;
	private double center_y;
	// Strza�
	public Shot shot;
	private boolean just_spawned;
	private int shot_delay;
	private boolean is_special;
	
	/**
	 * Ten konstruktor inicjalizuje podstawowe parametry dla obiektu wroga.
	 * Odczytuje obraz, bierze z niego �rednic� (zak�adamy, �e wczytany obraz jest ko�em)
	 * i tworzymy dla niego pusty strza�.
	 */
	public Enemy()
	{
		setImageIcon(ENEMY_IMG);	// Wczytujemy domy�lny sprite.
		diameter = getImageIcon().getImage().getWidth(null);	// Pobieramy szeroko�� sprite'a
		mov_speed = ENEMY_SPEED;
		is_special = false;
		
		// Strza�
		shot = new Shot();
		shot.setVisible(false);
	}
	
	/**
	 * Ta metoda ustawia wroga w losowym miejscu nie bli�ej ni� warto�� w interfejsie Commons
	 * od �rodka gracza podanego w argumentach.
	 * @param player_cent_x Pozycja X �rodka obiektu gracza.
	 * @param player_cent_y Pozycja Y �rodka obiektu gracza.
	 */
	public void spawn(int player_cent_x, int player_cent_y)
	{
		Random generator = new Random();
		
		// Wsp�rz�dzna x
		do
			pos_x = generator.nextInt(BOARD_WIDTH);
		while (pos_x > player_cent_x - ENEMY_SPAWN_DISTANCE && pos_x < player_cent_x + ENEMY_SPAWN_DISTANCE);
		
		if (pos_x < 0)
			pos_x = 0;
		
		if (pos_x > BOARD_WIDTH - diameter)
			pos_x = BOARD_WIDTH - diameter;
		
		center_x = pos_x + diameter / 2;
		
		// Wsp�rz�dzna y
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
	 * Ta metoda m�wi czy dany wr�g jest specjalnym wrogiem.
	 * @return true, je�li wr�g jest specjalny.
	 */
	public boolean getSpecial() { return is_special; }
	
	/**
	 * Ta metoda zmienia rodzaj danego wroga i zmienia odpowiednio obraz reprezentuj�cy go.
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
	 * Ta metoda zmienia pozycj� obiektu zgodnie z pozycj� �rodka gracza.
	 * Obiekt wroga zbli�a si� do gracza.
	 * @param player_cent_x Pozycja X �rodka obiektu gracza.
	 * @param player_cent_y Pozycja Y �rodka obiektu gracza.
	 */
	public void move(int player_cent_x, int player_cent_y)
	{
		// Wsp�rz�dna x
		if (player_cent_x > center_x)
			pos_x += mov_speed;
		else
			pos_x -= mov_speed;
		
		center_x = pos_x + diameter / 2;
		
		// Wsp�rz�dna y
		if (player_cent_y > center_y)
			pos_y += mov_speed;
		else
			pos_y -= mov_speed;
		
		center_y = pos_y + diameter / 2;
	}
	
	/**
	 * Ta metoda ustala czy obiekt koliduje si� z ko�em podanym jako parametry.
	 * @param x Pozycja X �rodka drugiego ko�a.
	 * @param y Pozycja Y �rodka drugiego ko�a.
	 * @param diameter �rednica drugiego ko�a.
	 * @return true, je�li dystans pomi�dzy �rodkami k� jest kr�tszy od sumy promieni k�.
	 */
	public boolean isCollided(int x, int y, int diameter)
	{
		// Je�li odleg�o�� pomi�dzy �rodkami ko�a jest kr�tsza od sumy promieni, ko�a si� koliduj�
		if ((center_x - x) * (center_x - x) + (center_y - y) * (center_y - y) < (this.diameter / 2 + diameter / 2) * (this.diameter / 2 + diameter / 2))
			return true;
		else
			return false;
	}
	
	/**
	 * Ta metoda tworzy nowy strza� wroga je�li mo�e w kierunku podanym jako argumenty.
	 * @param player_cent_x Pozycja X �rodka obiektu gracza.
	 * @param player_cent_y Pozycja Y �rodka obiektu gracza.
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
