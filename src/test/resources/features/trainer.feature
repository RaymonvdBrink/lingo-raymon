Feature: Training for Lingo
  As a lingo player,
  I want to guess 5,6 and 7 letter words,
  In order to prepare for lingo

Scenario: Start new game
  When I start a new game
  Then I should see the first letter
  And the word to guess has "5" letters
  And my score is "0"

Scenario Outline: Start a new round
  Given I am playing a game
  And the round was won
  And the last word had "<previous length>" letters
  When I start a new round
  Then the word to guess has "<next length>" letters

  Examples:
    | previous length | next length |
    | 5               | 6           |
    | 6               | 7           |
    | 7               | 5           |

# Failure path
  Given I am playing a game
  And the round was lost
  Then I cannot start a new round

  Scenario Outline: Guessing a word
    Given I am playing a round
    And the word to guess is "word"
    When I my "guess" is
    Then the the feedback is "feedback"

    Examples:
      | word | guess | feedback                                           |
      |BAARD | BERGEN|INVALID, INVALID, INVALID, INVALID, INVALID, INVALID|
      |BAARD | BONJE |CORRECT, ABSENT, ABSENT, ABSENT, ABSENT             |
      |BAARD | BARST |CORRECT, CORRECT, PRESENT, ABSENT, ABSENT           |
      |BAARD | DRAAD |ABSENT, PRESENT, CORRECT, PRESENT, CORRECT          |
      |BAARD | BAARD |CORRECT, CORRECT, CORRECT, CORRECT, CORRECT         |


  # Failure path
    Given I am playing a round
    And I am out of guess
    Then I lose the round
