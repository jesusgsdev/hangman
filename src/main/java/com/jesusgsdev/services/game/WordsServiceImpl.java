package com.jesusgsdev.services.game;

import com.jesusgsdev.constants.GameConstants;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jesgarsal on 22/04/17.
 */
@Service
public class WordsServiceImpl implements WordsService {

    @Override
    public String getSecretWord() {
        Integer randomIndex = ThreadLocalRandom.current().nextInt(0, GameConstants.availableWords.size());
        return GameConstants.availableWords.get(randomIndex);
    }

    @Override
    public String getVisibleWord(String originalWord) {
        return String.join("", Collections.nCopies(originalWord.length(), "?"));
    }

}
