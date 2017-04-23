package com.jesusgsdev.entities.game;

import com.jesusgsdev.constants.GameConstants;
import com.jesusgsdev.entities.users.Player;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by jesgarsal on 19/04/17.
 */
public class Game {

    private final Integer NUMBER_OF_ATTEMPTS = new Integer(5);

    private Player player;
    private String secretWord;
    private String visibleWord;
    private Integer attemptsLeft;
    private List<Character> availableCharacters;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private GameStatus status;

    public Game(Player player, String secretWord, String visibleWord) {
        this.player = player;
        this.secretWord = secretWord;
        this.visibleWord = visibleWord;
        this.attemptsLeft = NUMBER_OF_ATTEMPTS;
        this.availableCharacters = new LinkedList<>(GameConstants.initialCharacters);
        this.startDate = LocalDateTime.now();
        this.status = GameStatus.ACTIVE;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public String getVisibleWord() {
        return visibleWord;
    }

    public void setVisibleWord(String visibleWord) {
        this.visibleWord = visibleWord;
    }

    public Integer getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(Integer attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public List<Character> getAvailableCharacters() {
        return availableCharacters;
    }

    public void setAvailableCharacters(List<Character> availableCharacters) {
        this.availableCharacters = availableCharacters;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equals(player, game.player) &&
                Objects.equals(secretWord, game.secretWord) &&
                Objects.equals(visibleWord, game.visibleWord) &&
                Objects.equals(attemptsLeft, game.attemptsLeft) &&
                Objects.equals(availableCharacters, game.availableCharacters) &&
                Objects.equals(startDate, game.startDate) &&
                Objects.equals(endDate, game.endDate) &&
                status == game.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, secretWord, visibleWord, attemptsLeft, availableCharacters, startDate, endDate, status);
    }

}
