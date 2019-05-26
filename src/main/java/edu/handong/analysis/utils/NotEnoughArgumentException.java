package edu.handong.analysis.utils;

public class NotEnoughArgumentException extends Exception {
	
	public NotEnoughArgumentException() {
		super("edu.handong.analysise.utils.NotEnoughArgumentException occurred!");
	}
	
	public NotEnoughArgumentException(String message) {
		super(message);
	}
}
