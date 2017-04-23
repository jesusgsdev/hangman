package com.jesusgsdev.services.game;


import com.jesusgsdev.HangmanApplication;
import com.jesusgsdev.constants.GameConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;


/**
 * Created by jesgarsal on 22/04/17.
 */
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordsServiceTest {

    @Autowired
    private WordsService wordsService;

    @Test
    public void getARandomWord(){
        String randomWord = wordsService.getSecretWord();
        assertThat(GameConstants.availableWords, hasItem(randomWord));
    }

    @Test
    public void givenAWordOfSixLettersThenGetSixHyphens(){
        String originalWord = "Marble";
        String secretWord = wordsService.getVisibleWord(originalWord);
        String sixHyphens = "??????";
        assertThat(secretWord, is(sixHyphens));
    }
}
