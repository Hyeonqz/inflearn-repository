package org.hyeonqz.practicaltest.unit.beverage;

public class Americano implements Beverage {

	@Override
	public int gerPrice () {
		return 4000;
	}

	@Override
	public String getName () {
		return "아메리카노";
	}

}
