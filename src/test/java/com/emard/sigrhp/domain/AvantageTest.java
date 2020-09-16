package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class AvantageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avantage.class);
        Avantage avantage1 = new Avantage();
        avantage1.setId(1L);
        Avantage avantage2 = new Avantage();
        avantage2.setId(avantage1.getId());
        assertThat(avantage1).isEqualTo(avantage2);
        avantage2.setId(2L);
        assertThat(avantage1).isNotEqualTo(avantage2);
        avantage1.setId(null);
        assertThat(avantage1).isNotEqualTo(avantage2);
    }
}
