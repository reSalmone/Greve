package entity;

import main.GamePanel;

public class Projectile extends Entity{
	public Entity thrower;
	
	public Projectile(GamePanel gp, Entity thrower) {
		super(gp);
		this.thrower = thrower;
	}
}
