package com.jesusgsdev.dto;

import com.jesusgsdev.entities.game.Game;
import com.jesusgsdev.entities.game.GameStatus;

import java.util.List;
import java.util.Objects;

/**
 * Created by jesgarsal on 19/04/17.
 */
public class GameForPlayerDTO {

    private String visibleWord;
    private Integer attemptsLeft;
    private List<Character> availableCharacters;
    private String status;
    private String secretWord;

    public GameForPlayerDTO(Game game) {
        this.visibleWord = game.getVisibleWord();
        this.attemptsLeft = game.getAttemptsLeft();
        this.availableCharacters = game.getAvailableCharacters();
        this.status = game.getStatus().toString();
        this.secretWord = game.getStatus().equals(GameStatus.LOST) ? game.getSecretWord() : "";
    }

    public String getVisibleWord() {
        return visibleWord;
    }

    public Integer getAttemptsLeft() {
        return attemptsLeft;
    }

    public List<Character> getAvailableCharacters() {
        return availableCharacters;
    }

    public String getStatus() {
        return status;
    }

    public String getSecretWord() {
        return secretWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameForPlayerDTO)) return false;
        GameForPlayerDTO that = (GameForPlayerDTO) o;
        return Objects.equals(visibleWord, that.visibleWord) &&
                Objects.equals(attemptsLeft, that.attemptsLeft) &&
                Objects.equals(availableCharacters, that.availableCharacters) &&
                Objects.equals(status, that.status) &&
                Objects.equals(secretWord, that.secretWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visibleWord, attemptsLeft, availableCharacters, status, secretWord);
    }
}

