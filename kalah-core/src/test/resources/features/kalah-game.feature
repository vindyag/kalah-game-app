Feature: Start a new kalah game and play

  Scenario: Start a new kalah game and play
    Given A New Game is started
    Then Game should reflect the initial game state
    When Player makes a move by sowing seeds in Pit 1
    Then Game should reflect the move
