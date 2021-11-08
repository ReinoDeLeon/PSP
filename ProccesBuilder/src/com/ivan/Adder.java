package com.ivan;

public class Adder {
	private final int _startNumber;
	private final int _endNumber;
	private final String _processName;
	
	public Adder (int startNumber, int endNumber, String processName) {
		_startNumber = startNumber;
		_endNumber = endNumber;
		_processName = processName;
		
	}
	public int add() {
		int result = 0;
		for (int i = _startNumber; i < _endNumber; i++) {
			System.out.print(String.format("Iteración Nº%d %s --> %d + %d = ", i, _processName,result, i));
			result += i;
			System.out.print(result + "\n");
		}
		return result;
	}
	
	
	@Override
	public String toString() {
		return "Adder [_startNumber=" + _startNumber + ", _endNumber=" + _endNumber + "]";
	}
	
	public static void main(String[] args) {
		int startNumer = Integer.parseInt(args[0]);
		int endNumber = Integer.parseInt(args[1]);
		var processName = args[2];
		Adder adder = new Adder(startNumer, endNumber, processName);
		int result = adder.add();
		System.out.println(result);
		
	}
}
