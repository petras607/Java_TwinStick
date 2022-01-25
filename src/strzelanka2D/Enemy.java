package strzelanka2D;

import java.util.Random;

/**
 * Ta klasa reprezentuje wrog�w do ustrzelenia.
 * @author Piotr Pieni��ek
 */
public class Enemy extends Object implements Commons
{
	private int diameter;	// �rednica do wyznaczania kolizji
	// Pozycja centrum dla wyznaczania kolizji
	//private double center_x;
	//private double center_y;
	private int shot_cooldown = 0;
	
	/**
	 * Ten konstruktor inicjalizuje podstawowe parametry dla obiektu wroga.
	 * Odczytuje obraz, bierze z niego �rednic� (zak�adamy, �e wczytany obraz jest ko�em) i
	 * ustawia wroga w losowym miejscu nie bli�ej ni� warto�� w interfejsie Commons
	 * od �rodka gracza podanego w argumentach.
	 * @param player_cent_x Pozycja X �rodka obiektu gracza.
	 * @param player_cent_y Pozycja Y �rodka obiektu gracza.
	 */
	public Enemy(int player_cent_x, int player_cent_y, boolean is_special)
	{
		setImageIcon(ENEMY_IMG);	// Wczytujemy domy�lny sprite.
		diameter = getImageIcon().getImage().getWidth(null);	// Pobieramy szeroko�� sprite'a
		mov_speed = ENEMY_SPEED;
		is_special = false;
		
		Random generator = new Random();
		
		// Wsp�rz�dzna x
		do
			pos_x = generator.nextInt(BOARD_WIDTH);
		while (pos_x > player_cent_x - ENEMY_SPAWN_DISTANCE && pos_x < player_cent_x + ENEMY_SPAWN_DISTANCE);
		
		if (pos_x < 0)
			pos_x = 0;
		
		if (pos_x > BOARD_WIDTH - diameter)
			pos_x = BOARD_WIDTH - diameter;
		
		// Wsp�rz�dzna y
		do
			pos_y = generator.nextInt(BOARD_HEIGHT);
		while (pos_y > player_cent_y - ENEMY_SPAWN_DISTANCE && pos_y < player_cent_y + ENEMY_SPAWN_DISTANCE);
		
		if (pos_y < 0)
			pos_y = 0;
		
		if (pos_y > BOARD_HEIGHT - diameter)
			pos_y = BOARD_HEIGHT - diameter;
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
		if (player_cent_x > pos_x + diameter / 2)
			pos_x += mov_speed;
		else
			pos_x -= mov_speed;
		
		// Wsp�rz�dna y
		if (player_cent_y > pos_y + diameter / 2)
			pos_y += mov_speed;
		else
			pos_y -= mov_speed;
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
		double center_x = pos_x + diameter / 2;
		double center_y = pos_y + diameter / 2;
		// Je�li odleg�o�� pomi�dzy �rodkami ko�a jest kr�tsza od sumy promieni, ko�a si� koliduj�
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
	 * Ta metoda odczytuje koordynat X �rodka obiektu.
	 * @return Koordynat X �rodka obiektu.
	 */
	public int getCenterX() { return (int) Math.round(pos_x + diameter / 2);}
	
	/**
	 * Ta metoda odczytuje koordynat Y �rodka obiektu.
	 * @return Koordynat Y �rodka obiektu.
	 */
	public int getCenterY() { return (int) Math.round(pos_y + diameter / 2);}
	
	/**
	 * Ta metoda tworzy nowy strza� wroga je�li mo�e w kierunku podanym jako argumenty.
	 * @param player_cent_x Pozycja X �rodka obiektu gracza.
	 * @param player_cent_y Pozycja Y �rodka obiektu gracza.
	 */
	public Shot shoot(int player_cent_x, int player_cent_y)
	{
		shot_cooldown = ENEMY_SHOT_DELAY;
		return new Shot((int) Math.round(pos_x + diameter / 2), (int) Math.round(pos_y + diameter / 2), player_cent_x, player_cent_y, ENEMY_SHOT_SPEED, ENEMY_SHOT_IMG);
	}
}
