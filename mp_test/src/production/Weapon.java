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
	private String key = "p";
	@XmlElement
	private boolean keyDownPossible = false;
	@XmlElement
	private boolean fireOnRelease = false;
	@XmlElement
	private boolean animOnRelease = false;
	@XmlElement
	private double maxEnergy = 0;

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

	private void spawnProjectiles() {
		if (projectiles[0] != "none") {
			double pOffsetX = pOffset[0];
			double pOffsetY = pOffset[1];
			for (int i = 0; i < projectiles.length; i++) {
				if (pOffset.length > i * 2) {
					pOffsetX = pOffset[i * 2];
					pOffsetY = pOffset[i * 2 + 1];
				}
				Projectile test = (Projectile) JavaAndXML.getInstance()
						.XMLtoJava(Controller.projectiles.get(projectiles[i]),
								Projectile.class);
				test.setPosition(x + pOffsetX, y + pOffsetY);
				// Controller.spawn(Controller.projectiles, projectiles[i], x +
				// pOffsetX, y+ pOffsetY, Projectile.class);
			}
		}
	}

	public void fireWeapon(double energy) {
		spawnProjectiles();
	}

	public void firePressed() {
		if (!fireOnRelease && cooldownLeft <= 0) {
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
		if (fireOnRelease && cooldownLeft <= 0) {
			cooldownLeft = cooldown;
			fireWeapon(energyCollected);
			energyCollected = 0;
			System.out.println("fired at: " + energyCollected);
		}
		if (animOnRelease) {
			layers.get(0).playAnimation();
		}

		if (layers.size() > 1) {
			layers.get(1).stopAnimation();
		}
	}

	@Override
	public void keyDown(String key) {
		firePressed();
		if (keyDownPossible) {
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
		InputControl.getInstance().registerKey(key, this);
	}

	@Override
	public void unregisterControl() {
		InputControl.getInstance().unregisterKey(key);
	}

	@Override
	public void destroyObject() {
		super.destroyObject();
		unregisterControl();
	}
}
