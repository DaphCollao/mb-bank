package com.mindHub.HomeBanking;

import com.mindHub.HomeBanking.Utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class UtilsTest {
    @Test
    public void cardNumberIsCreated(){
        int cardNumber = (Utils.getRandomNumber(500,900));
        assertThat(cardNumber,is(greaterThan(0)));
    }
}
