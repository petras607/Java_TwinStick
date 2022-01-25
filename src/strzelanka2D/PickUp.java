package strzelanka2D;

import java.time.LocalTime;
import java.util.Random;

public class PickUp extends Object implements Commons
{
	public boolean is_bomb;
	private int width;
	public int spawnSec;
	
	public PickUp(int start_x, int start_y, boolean is_bomb)
	{
		this.is_bomb = is_bomb;
		
		if (is_bomb)
			setImageIcon(BOMB_IMG);
		else
			setImageIcon(PICKUP_IMG);
		
		width = getImageIcon().getImage().getWidth(null);
		
		// Wspó³rzêdne
		pos_x = start_x - width / 2;
		pos_y = start_y - width / 2;
		
		// Kierunek dobieramy losowo
		Random generator = new Random();
		mov_x = generator.nextInt(20) - 10;
		
		do
		{
			mov_y = generator.nextInt(20) - 10;
		} while (mov_y == 0);
		
		if (mov_y < 0)
			mov_y = -Math.sqrt(10 * 10 - mov_x * mov_x) * PICKUP_START_SPEED;
		else
			mov_y = Math.sqrt(10 * 10 - mov_x * mov_x) * PICKUP_START_SPEED;
		
		// Pocz¹tek "¿ycia"
		spawnSec = LocalTime.now().getSecond();
	}
	
	public void move()
	{
		if (mov_x > 0)
		{
			pos_x += mov_x;
			
			if (mov_x - PICKUP_SLOWDOWN_RATE < 0)
				mov_x = 0;
			else
				mov_x -= PICKUP_SLOWDOWN_RATE;
			
			if (pos_x + width > BOARD_WIDTH)
			{
				pos_x = BOARD_WIDTH - (pos_x - BOARD_WIDTH);
				mov_x = -mov_x;
			}
		}
		else if (mov_x < 0)
		{
			pos_x += mov_x;
			
			if (mov_x + PICKUP_SLOWDOWN_RATE > 0)
				mov_x = 0;
			else
				mov_x += PICKUP_SLOWDOWN_RATE;
			
			if (pos_x < 0)
			{
				pos_x = - pos_x;
				mov_x = -mov_x;
			}
		}
		
		if (mov_y > 0)
		{
			pos_y += mov_y;
			
			if (mov_y - PICKUP_SLOWDOWN_RATE < 0)
				mov_y = 0;
			else
				mov_y -= PICKUP_SLOWDOWN_RATE;
			
			if (pos_y + width > BOARD_HEIGHT)
			{
				pos_y = BOARD_HEIGHT - (pos_y - BOARD_HEIGHT);
				mov_y = -mov_y;
			}
		}
		else if (mov_y < 0)
		{
			pos_y += mov_y;
			
			if (mov_y + PICKUP_SLOWDOWN_RATE > 0)
				mov_y = 0;
			else
				mov_y += PICKUP_SLOWDOWN_RATE;
			
			if (pos_y < 0)
			{
				pos_y = - pos_y;
				mov_y = -mov_y;
			}
		}
	}
	
	public boolean isCollided(int x, int y, int diameter)
	{
		double center_x = pos_x + width / 2;
		double center_y = pos_y + width / 2;
		
		// 1. Czy odleg³oœæ miêdzy œrodkami jest krótsza od sumy promienia i po³owy szerokoœci
		if ((center_x - x) * (center_x - x) + (center_y - y) * (center_y - y) < (width / 2 + diameter / 2) * (width / 2 + diameter / 2))
			return true;
		
		// 2. Czy któryœ z rogów znajduje siê wewn¹trz ko³a
		if ((Math.abs(pos_x - x) < diameter && Math.abs(pos_y - y) < diameter) ||
			(Math.abs(pos_x + width - x) < diameter && Math.abs(pos_y - y) < diameter) ||
			(Math.abs(pos_x - x) < diameter && Math.abs(pos_y + width - y) < diameter) ||
			(Math.abs(pos_x + width - x) < diameter && Math.abs(pos_y + width - y) < diameter))
			return true;
		
		return false;
	}
}
