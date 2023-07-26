/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.battleship.test;

import fr.uga.battleship.Coordinate;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */
public class TestCoordinate {

	// before have to implement Coordinate(String c), getRow(), getColumn() and
	// toString()
	public static void testValues(String v, int row, int col) {

		Coordinate c = new Coordinate(v);
		if (c.getRow() != row) {
			System.out.println("Problem with row. Should be " + row + " but is " + c.getRow());
		}
		if (c.getColumn() != col) {
			System.out.println("Problem with column. Should be " + col + " but is " + c.getColumn());
		}
		if (!v.endsWith(c.toString())) {
			System.out.println("Problem with toString. Should be " + v + " but is " + c);
		}
	}

	public static void testValues(int row, int col) {

		Coordinate c = new Coordinate(row, col);
		if (c.getRow() != row) {
			System.out.println("Problem with row. Should be " + row + " but is " + c.getRow());
		}
		if (c.getColumn() != col) {
			System.out.println("Problem with column. Should be " + col + " but is " + c.getColumn());
		}
	}

	public static void testExceptionNegative() {
		try {
			new Coordinate(0, -1);
			System.out.println("Problem a negative column should have been detected");
		} catch (Exception e) {

		}
		try {
			new Coordinate(-1, 0);
			System.out.println("Problem a negative row should have been detected");
		} catch (Exception e) {

		}
		try {
			new Coordinate(-1, -1);
			System.out.println("Problem a negative row and column should have been detected");
		} catch (Exception e) {

		}
	}

	public static void testExceptionParsing(String s) {
		try {
			new Coordinate(s);
			System.out.println("Problem " + s + " should not have been parsed");
		} catch (Exception e) {

		}
	}

	public static void testEquality() {
		Coordinate c1 = new Coordinate(3, 4);
		Coordinate c2 = new Coordinate(3, 4);
		Coordinate c3 = new Coordinate(4, 3);
		Coordinate c4 = new Coordinate(3, 5);
		Coordinate c5 = new Coordinate(2, 4);
		if (!c1.equals(c2)) {
			System.out.println("Problem: " + c1 + " should be equals to " + c2);
		}
		if (c1.equals(c3)) {
			System.out.println("Problem: " + c1 + " should not be equals to " + c3);
		}
		if (c1.equals(c4)) {
			System.out.println("Problem: " + c1 + " should not be equals to " + c4);
		}
		if (c1.equals(c5)) {
			System.out.println("Problem: " + c1 + " should not be equals to " + c5);
		}
	}

	public static void testCompareTo() {
		Coordinate c1 = new Coordinate(3, 4);
		Coordinate c2 = new Coordinate(3, 4);
		Coordinate c3 = new Coordinate(4, 3);
		Coordinate c4 = new Coordinate(3, 5);
		Coordinate c5 = new Coordinate(2, 4);
		if (c1.compareTo(c2) != 0) {
			System.out.println("Problem: " + c1.compareTo(c2) + " ");
		}
		if (c1.equals(c3)) {
			System.out.println("Problem: " + c1 + " should not be equals to " + c3);
		}
		if (c1.equals(c4)) {
			System.out.println("Problem: " + c1 + " should not be equals to " + c4);
		}
		if (c1.equals(c5)) {
			System.out.println("Problem: " + c1 + " should not be equals to " + c5);
		}
	}

	public static void main(String[] args) {

		testValues("A1", 0, 0);
		testValues("J10", 9, 9);
		testValues("Z20", 25, 19);

		testValues(1, 1);
		testValues(33, 23);
		testExceptionNegative();

		testExceptionParsing("toto");
		testExceptionParsing("A-20");
		testExceptionParsing("AA1");

	}
}
