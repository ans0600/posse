package com.mower.lawn;

import java.util.ArrayList;

import java.util.HashMap;

import com.mower.Mower;
import com.mower.exception.FatalException;

public class Lawn extends Grid {

	private ArrayList<Mower> mowers;
	private HashMap<Coordinate, Coordinate> coordinateSet;

	public Lawn(Coordinate boundry) {
		super(boundry);
		this.mowers = new ArrayList<Mower>();
	}

	public void addMower(Mower m) {
		m.setLawn(this);
		this.mowers.add(m);
	}

	/**
	 * Complexity o(n*m) where m is number of mowers and n is length of the
	 * longest command
	 * 
	 */
	public void startMowers() {
		if (this.mowers.size() > 0) {
			boolean hasCommand = false;
			do {
				// clear the coordinateSet for this move step
				this.coordinateSet = new HashMap<Coordinate, Coordinate>();
				hasCommand = false;
				for (int i = 0; i < this.mowers.size(); i++) {

					Mower m = this.mowers.get(i);
					boolean res = m.executeCommand();
					System.err.println(i + " " + res);
					hasCommand = hasCommand || res;

					this.hasCollision(m);

				}

			} while (hasCommand);
		}

	}

	public boolean hasCollision(Mower m) {
		return this.hasCollision(m.getInitPosition())
				|| this.hasCollision(m.getCurrentLocation());

	}

	private boolean hasCollision(MowerCoordinate c) {
		// System.err.println("Has Collision "+c.toString());
		if (this.coordinateSet.containsKey(c)) {
			System.err.println("Key Exist: " + c.toString());
			MowerCoordinate existing = (MowerCoordinate) this.coordinateSet
					.get(c);
			if (c.getType() == existing.getType()) {
				System.err.println("Collision Type A!!");
				return true;
			} else if (Math.abs(c.getFacing() - existing.getFacing()) == 180) {
				System.err.println("Collision Type B");
				return true;
			}

		} else {
			this.coordinateSet.put(c, c);
		}
		return false;
	}

	/**
	 * For part 2
	 * 
	 * @param origin
	 * @return
	 */
	public MowerCoordinate getAdjacentCoordinate(MowerCoordinate origin) {
		MowerCoordinate dest = origin.action('M');

		if (this.isInside(dest) && !this.visited[origin.x][origin.y]) {
			this.visited[origin.x][origin.y] = true;
			return dest;

		} else {
			return null;
		}

	}

	/**
	 * For part 2
	 * 
	 * @param numOfMowers
	 */
	public void getMowerAssignment(int numOfMowers) {

	}

}
