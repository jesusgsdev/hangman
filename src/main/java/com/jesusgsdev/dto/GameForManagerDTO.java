package com.jesusgsdev.dto;

import com.jesusgsdev.entities.game.Game;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Created by jesgarsal on 19/04/17.
 */
public class GameForManagerDTO {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private String username;
    private String secretWord;
    private String visibleWord;
    private String attemptsLeft;
    private String startDate;
    private String endDate;
    private String status;

    public GameForManagerDTO(Game game) {
        this.username = game.getPlayer().getUsername();
        this.secretWord = game.getSecretWord();
        this.visibleWord = game.getVisibleWord();
        this.attemptsLeft = game.getAttemptsLeft().toString();
        this.startDate = game.getStartDate().format(formatter);
        this.endDate = nonNull(game.getEndDate()) ? game.getEndDate().format(formatter) : "-";
        this.status = game.getStatus().toString();
    }

    public String getUsername() {
        return username;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public String getVisibleWord() {
        return visibleWord;
    }

    public String getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameForManagerDTO)) return false;
        GameForManagerDTO that = (GameForManagerDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(secretWord, that.secretWord) &&
                Objects.equals(visibleWord, that.visibleWord) &&
                Objects.equals(attemptsLeft, that.attemptsLeft) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, secretWord, visibleWord, attemptsLeft, startDate, endDate, status);
    }
}
