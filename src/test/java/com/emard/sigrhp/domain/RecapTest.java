package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class RecapTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recap.class);
        Recap recap1 = new Recap();
        recap1.setId(1L);
        Recap recap2 = new Recap();
        recap2.setId(recap1.getId());
        assertThat(recap1).isEqualTo(recap2);
        recap2.setId(2L);
        assertThat(recap1).isNotEqualTo(recap2);
        recap1.setId(null);
        assertThat(recap1).isNotEqualTo(recap2);
    }
}
