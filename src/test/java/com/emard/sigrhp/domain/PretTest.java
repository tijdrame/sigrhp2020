package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class PretTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pret.class);
        Pret pret1 = new Pret();
        pret1.setId(1L);
        Pret pret2 = new Pret();
        pret2.setId(pret1.getId());
        assertThat(pret1).isEqualTo(pret2);
        pret2.setId(2L);
        assertThat(pret1).isNotEqualTo(pret2);
        pret1.setId(null);
        assertThat(pret1).isNotEqualTo(pret2);
    }
}
