Feature: Training for Lingo
  As a lingo player,
  I want to guess 5,6 and 7 letter words,
  In order to prepare for lingo

Scenario: Start new game
  When I start a new game
  Then I should see the first letter
  And the word to guess has "5" letters
  And my score is "0"

