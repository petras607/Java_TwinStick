package strzelanka2D;

/**
 * Ta klasa reprezentuje strza³y pochodz¹ce od gracza i wrogów.
 * @author Piotr Pieni¹¿ek
 */
public class Shot extends Object implements Commons
{
	private int diameter;
	// Wektor ruchu
	private double length_x;
	private double length_y;
	private double length;
	
	/**
	 * Ten konstruktor tworzy strza³ z pozycj¹ centrum, kierunkiem i prêdkoœci¹ strza³u podawanymi w argumentach, którego reprezentuje podany obraz.
	 * @param start_x Koordynat X centrum strza³u.
	 * @param start_y Koordynat Y centrum strza³u.
	 * @param direction_x Koordynat X kierunku strza³u.
	 * @param direction_y Koordynat Y kierunku strza³u.
	 * @param shot_speed Prêdkoœæ strza³u.
	 * @param shot_Img Obraz reprezentuj¹cy dany strza³. Strza³ wroga ma inny obraz od strza³y gracza. 
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
	 * Ta metoda zmienia pozycjê strza³u zgodnie z kierunkiem podanym podczas konstruowania.
	 */
	public void move()
	{
		pos_x += mov_speed * length_x / length;
		pos_y += mov_speed * length_y / length;
	}
	
	/**
	 * Ta metoda oblicza k¹t obrotu strza³u korzystaj¹c z funkcji arcus sinus.
	 * @return K¹t obrotu w radianach.
	 */
	public double getRotation()
	{
		double radian = Math.asin(length_y / length) + Math.PI / 2;
		
		if (length_x < 0)
			radian = 2 *Math.PI - radian;
		
		return radian;
	}
	
	/**
	 * Ta metoda odczytuje œrednicê obiektu.
	 * @return Œrednica.
	 */
	public int getDiameter() { return diameter; }
	
	/**
	 * Ta metoda odczytuje koordynat X œrodka obiektu.
	 * @return Koordynat X œrodka obiektu.
	 */
	public int getCenterX() { return (int) Math.round(pos_x + diameter / 2); }
	
	/**
	 * Ta metoda odczytuje koordynat Y œrodka obiektu.
	 * @return Koordynat Y œrodka obiektu.
	 */
	public int getCenterY() { return (int) Math.round(pos_y + diameter / 2); }
}
