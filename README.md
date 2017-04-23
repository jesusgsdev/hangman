[![Build Status](https://travis-ci.org/jesusgsdev/hangman.svg?branch=master)](https://travis-ci.org/jesusgsdev/hangman)
# Hangman
A Hangman version on Browser and Spring Boot.

## Instructions
There are two profiles: Players and a Manager.

## How to enter into the Manager view
At the login screen, type MANAGER (capital letters are required) and then click on the Loginb button. Then you will see the manager dashboard.

## How to enter as a Player and play
At the login screen, type your username (any string you want) and click on the Login button.
After being logged, click on Start/Continue Game in order to start a new game if it's your first time or continue the last game you were playing.

## How to play
Once you have been logged as a Player and started a Game, you will see the current status of the game, the current visible word, the number of attempts left and a list of letter to choose between them to guess the hidden word.
Every time you click on a letter, it will be deleted from the list and the word will be updated. If your letter was within the word, you will keep the same number of attempts left, otherwise it will decrease by one.
If you use all your attempts and you didn't guess the word, then the original word will be revealed and you will be forced to start a new game for keep playing.

## Manager dashboard
From the manager dashboard you will be able to see the current status of all the games (current ones and past ones) and you will be able to do filtering/searches, ordering by the different columns, etc.

#### I used a TDD approach to achieve this project
#### There are a total of 23 tests to ensure everything is working as expected
#### The current code coverage is 91% on Classes and 76% on lines