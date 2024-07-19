package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

	// We could create Questions class
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

	// TODO : Categories of questions, should go to a class with enum instead of string
	Map<string, LinkedList> mapCategoriesQuestion = (
			{"Pop", popQuestions},
			{"Science", scienceQuestions},
			{"Sports", sportsQuestions},
			{"Rock", rockQuestions}
	);

    public  Game(Player player1; Player player2){
		this.players.add(player1);
		this.players.add(player2);
		this.initializeQuestions();
    }

	/**
	 * TODO : Javadoc
	 */
	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	/**
	 * TODO : Javadoc
	 */
	// TODO : with constructor limit, doesn't need anymore
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	/**
	 * TODO : Javadoc
	 */
	public boolean add(String playerName) {
		// Limit number of players
		if(players.size() == places.size()) {
			System.out.println(playerName + " was not added, limit of players already reached");
			return false;
		}
	    players.add(playerName);
		// Could throw ArrayOutOfBoundException if 8 players
		// One solution possible : force constructor with number of players (need multiple constructors so certainly better solution exist)
		// remove add possibility
		// and then initialize places and purses with correct size.
	    places[howManyPlayers()] = 0;
		// Could throw ArrayOutOfBoundException if 8 players
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	/**
	 * TODO : Javadoc
	 */
	public int howManyPlayers() {
		return players.size();
	}

	/**
	 * TODO : Javadoc
	 */
	public void roll(int roll) {
		// TODO : NullPointerException if any player added > limit number of players with constructor
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		// TODO : ArrayOutOfBoundException possible here, one possibility to resolve this :
		// add an attribute inPenaltyBox to class Player with enum Out/inPenaltyBox or boolean.
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			movePlayerAndAskQuestion(roll);
		}
		
	}

	/**
	 * Set all questions of the game for each categories
	 * All categories with same number of questions
	 */
	// we could now easily create other constructors with more players
	// if needed
	pivate void initializeQuestions() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}

	/**
	 * TODO : Javadoc
	 */
	private void movePlayerAndAskQuestion(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	/**
	 * TODO : Javadoc
	 */
	private void askQuestion() {
		// TODO : looks like we have to apply same code to all categories
		// if we need to change logic, we have to change for all, we could easily forget to change one
		// One solution possible : create Categories and apply code with a forEach loop
		this.mapCategoriesQuestion.forEach((category, questions) -> {
			System.out.println(questions.removeFirst());
		});
	}

	/**
	 * TODO : Javadoc
	 */
	private String currentCategory() {
		int position = places[currentPlayer] % 4;
		// TODO : looks like we have to apply same code to all categories
		// if we need to change logic, we have to change for all, we could easily forget to change one
		// One solution possible : create Categories and apply code with a forEach loop
		// + use enum for category
		if (position == 0) return "Pop";
		if (position == 1) return "Science";
		if (position == 2) return "Sports";
		if (position == 3) return "Rock";
	}

	/**
	 * TODO : Javadoc
	 */
	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}

	/**
	 * TODO : Javadoc
	 */
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}

	/**
	 * TODO : Javadoc
	 */
	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
