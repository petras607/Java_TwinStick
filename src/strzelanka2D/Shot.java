package strzelanka2D;

/**
 * Ta klasa reprezentuje strza�y pochodz�ce od gracza i wrog�w.
 * @author Piotr Pieni��ek
 */
public class Shot extends Object implements Commons
{
	private int diameter;
	// Wektor ruchu
	private double length_x;
	private double length_y;
	private double length;
	
	/**
	 * Ten konstruktor tworzy strza� z pozycj� centrum, kierunkiem i pr�dko�ci� strza�u podawanymi w argumentach, kt�rego reprezentuje podany obraz.
	 * @param start_x Koordynat X centrum strza�u.
	 * @param start_y Koordynat Y centrum strza�u.
	 * @param direction_x Koordynat X kierunku strza�u.
	 * @param direction_y Koordynat Y kierunku strza�u.
	 * @param shot_speed Pr�dko�� strza�u.
	 * @param shot_Img Obraz reprezentuj�cy dany strza�. Strza� wroga ma inny obraz od strza�y gracza. 
	 */
	public Shot(int start_x, int start_y, int direction_x, int direction_y, int shot_speed, String shot_Img)
	{
		// Konfiguracja obrazu
		setImageIcon(shot_Img);
		diameter = getImageIcon().getImage().getWidth(null);
		
		// Pozycja
		pos_x = start_x - diameter / 2;
		pos_y = start_y - diameter / 2;
		
		// Wektor ruchu
		length_x = direction_x - start_x;
		length_y = direction_y - start_y;
		length = Math.sqrt(length_x * length_x + length_y * length_y);
		
		mov_speed = shot_speed;
	}
	
	/**
	 * Ta metoda zmienia pozycj� strza�u zgodnie z kierunkiem podanym podczas konstruowania.
	 */
	public void move()
	{
		pos_x += mov_speed * length_x / length;
		pos_y += mov_speed * length_y / length;
	}
	
	/**
	 * Ta metoda oblicza k�t obrotu strza�u korzystaj�c z funkcji arcus sinus.
	 * @return K�t obrotu w radianach.
	 */
	public double getRotation()
	{
		double radian = Math.asin(length_y / length) + Math.PI / 2;
		
		if (length_x < 0)
			radian = 2 *Math.PI - radian;
		
		return radian;
	}
	
	/**
	 * Ta metoda odczytuje �rednic� obiektu.
	 * @return �rednica.
	 */
	public int getDiameter() { return diameter; }
	
	/**
	 * Ta metoda odczytuje koordynat X �rodka obiektu.
	 * @return Koordynat X �rodka obiektu.
	 */
	public int getCenterX() { return (int) Math.round(pos_x + diameter / 2); }
	
	/**
	 * Ta metoda odczytuje koordynat Y �rodka obiektu.
	 * @return Koordynat Y �rodka obiektu.
	 */
	public int getCenterY() { return (int) Math.round(pos_y + diameter / 2); }
}
