[![Build Status](https://travis-ci.org/cpe305/fall2016-project-cesiu.svg?branch=master)](https://travis-ci.org/cpe305/fall2016-project-cesiu)

![ui mock](https://github.com/cpe305/fall2016-project-cesiu/blob/master/diagrams/GameScreen.png?raw=true)

SmartRochambeau is a single player Rock-Paper-Scissors game featuring four
different AI opponents, developed as an individual project for Professor
Gudrun Socher's CPE 305 at Cal Poly, Fall 2016.

## Installing
Only the cross-platform SmartRochambeau.jar is required to run the game.

## Running
SmartRochambeau can be run by double clicking on the JAR or from the command 
line using `java -jar SmartRochambeau.jar`.

SmartRochambeau keeps track of win-loss-tie statistics and supports saved games.

## Opponents
SmartRochambeau features four AI opponents:
* A random AI as a control, which simply selects a random throw every round.
* A Markov Chain AI modeled using nine states and three intermediate states, 
  which answers the question, "Considering what the player threw last and 
  whether or not he won, what is he likely to throw next?". At any given time, 
  the current state is determined by the player's last throw and the result of 
  that round, e.g., "Rock, win". Each state contains frequencies logging how 
  often, when the game is at that current state, the player has moved according 
  to each of the three intermediate states, which contain only a throw, e.g., 
  "Paper". For example, the state "Rock, win" would contain three numbers 
  corresponding to how often, after playing Rock and winning, the player then 
  threw Rock, Paper, and Scissors. Based on those numbers, the AI can predict 
  the player's most likely next move. The AI trains as it runs, updating the 
  frequencies once it knows the outcome of the current round before it moves to
  the next state.
* A Naive Bayes AI -- implemented mostly as an experiment, not because I thought
  it was necessarily a good algorithm for this problem -- that attempts to
  answer the question, "Considering the player's last few throws, what is he
  leading up to throwing?". The idea being, a Markov-Chain-based is vulnerable
  because Rock-Paper-Scissors doesn't really satisfy the Markov Property; what
  the player does next is not solely dependant on what he did last. The Naive
  Bayes AI attempts to defeat 'longer' strategies wherein the player makes a
  few moves to set his opponent up for a later move. The AI maintains a queue
  of the player's last few throws as well as nine frequencies for how often each
  individual throw 'led up to' each of the other throws. For example, if the
  last three throws were "Rock, Paper, Rock", the AI can look at how often,
  historically, Rock and Paper have indivdually led up to Rock, Paper, or
  Scissors, then combine those probabilities using Bayes Rule. Like the Markov
  Chain AI, the Naive Bayes AI is capable of training as it goes, though this
  makes it vulnerable to overfitting and thus long term shifts in strategy.
* A Pattern Matching AI, which I personally predicted would do best of the 
  three, that attempts to answer the same question as the Naive Bayes AI, but
  based on the pattern of the individual throws, not their general overall
  frequencies. The AI maintains a complete ternary tree of throws to allow
  for faster pattern matching, with each possible pattern represented as a
  unique path from root to a leaf, and each leaf, as you might expect, contains
  frequencies for each of the three throws. The Pattern Matching AI, too, is
  capable of training as it runs..

These AI all share a common drawback: training as they go makes them easy to
set up and play immediately, but it makes them vulnerable to overfitting. A
player might make a slight change to his strategy, and the AI might take
a long time to catch up.

One possible solution is to count how many times the AI has used a throw for 
training. After a certain threshold, whether that be related to number of throws
trained on, win rate, or some combination thereof, the AI stops saving
information after each round. After a certain amount of time or after the win
rate drops below some other threshold, the AI can start training again, perhaps
partially or completely "wiping the slate" and deleting some or all of its saved
information.

## Architecture

![class diagram](https://github.com/cpe305/fall2016-project-cesiu/blob/master/diagrams/classDiagram.png?raw=true)

SmartRochambeau uses a modified Model-View-Controller architecture pattern, with
a controller class providing a generalized interface to the UI for the core
logic and also giving the UI access to methods of the core logic. Both the UI
and the AI implement interfaces designed to make adding new machine learning AI
easy. 
