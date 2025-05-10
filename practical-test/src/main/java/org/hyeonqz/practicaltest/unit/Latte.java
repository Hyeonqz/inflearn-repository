package org.hyeonqz.practicaltest.unit;

public class Latte implements Beverage {

	@Override
	public int gerPrice () {
		return 4500;
	}

	@Override
	public String getName () {
		return "라떼";
	}

}
