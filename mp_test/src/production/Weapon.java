package production;

import input.InputControl;
import input.InputInterface;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import reader.JavaAndXML;
import controller.Controller;

@XmlRootElement
public class Weapon extends GraphicalElement implements InputInterface {

	@XmlElement
	protected String test = "default";
	@XmlElement
	protected String[] projectiles = { "none" };
	@XmlElement
	private int[] offset = { 0, 0 };
	@XmlElement
	private int[] pOffset = { 0, 0 };
	@XmlElement
	private double cooldown = 100;
	@XmlElement
	private String[] keys = { "" };
	@XmlElement
	private boolean chargeWeapon = false;
	@XmlElement
	private boolean fastFire = false;
	@XmlElement
	private double maxEnergy = 0;
	@XmlElement
	private int[] powerLevels = { 0 };

	private boolean keyDown = false;
	private double cooldownLeft = 0;
	private double energyCollected = 0;

	public void init() {
		super.init();
		registerControl();
	}

	public void update(double delta) {
		super.update(delta);
		if (cooldownLeft > 0) {
			cooldownLeft -= delta;
		}
		if (keyDown) {
			if (energyCollected < maxEnergy) {
				energyCollected += delta;
			} else {
				energyCollected = maxEnergy;
			}
			firePressed();
		}
		this.x = owner.x + offset[0];
		this.y = owner.y + offset[1];
	}

	@Override
	public double[] getPosition() {
		return new double[] { owner.x + offset[0], owner.y + offset[1] };
	}

	private void spawnProjectile(int number) {
		// Projectile test =
		double pOffsetX = pOffset[0];
		double pOffsetY = pOffset[1];
		if (pOffset.length > number * 2) {
			pOffsetX = pOffset[number * 2];
			pOffsetY = pOffset[number * 2 + 1];
		}
		Projectile projectile = (Projectile) JavaAndXML.getInstance().XMLtoJava(Controller.getInstance().getXML("Weapons", projectiles[number]), Projectile.class);
		projectile.setPosition(x + pOffsetX, y + pOffsetY);
	}

	public void fireWeapon(double energy) {
		if (chargeWeapon) {
			int level = 0;
			for (int i = 0; i < powerLevels.length; i++) {
				System.out.println(energy + " compare against level: " + powerLevels[i]);
				if (energy >= powerLevels[i]) {
					level++;
				}
			}
			spawnProjectile(level);
		} else {
			for (int i = 0; i < projectiles.length; i++) {
				spawnProjectile(i);
			}
		}
	}

	public void firePressed() {
		if (!chargeWeapon && cooldownLeft <= 0) {
			layers.get(0).playAnimation();
			cooldownLeft = cooldown;
			fireWeapon(energyCollected);
		} else {
			if (layers.size() > 1) {
				layers.get(1).turnOnAnimation();
			}
		}
	}

	public void fireReleased() {
		if (chargeWeapon && cooldownLeft <= 0) {
			cooldownLeft = cooldown;
			fireWeapon(energyCollected);
			System.out.println("fired at: " + energyCollected);
			energyCollected = 0;
		}
		if (chargeWeapon) {
			layers.get(0).playAnimation();
		}

		if (layers.size() > 1) {
			layers.get(1).stopAnimation();
		}
	}

	@Override
	public void keyDown(String key) {
		firePressed();
		if (chargeWeapon || fastFire) {
			keyDown = true;
		}
	}

	@Override
	public void keyUp(String key) {
		keyDown = false;
		fireReleased();
	}

	@Override
	public void keyAction(String key) {
		firePressed();
		fireReleased();
	}

	@Override
	public void registerControl() {
		for (int i = 0; i < keys.length; i++) {
			InputControl.getInstance().registerKey(keys[i], this);
		}
	}

	@Override
	public void unregisterControl() {
		for (int i = 0; i < keys.length; i++) {
			InputControl.getInstance().unregisterKey(keys[i]);
		}
	}

	@Override
	public void destroyObject() {
		super.destroyObject();
		unregisterControl();
	}
}
