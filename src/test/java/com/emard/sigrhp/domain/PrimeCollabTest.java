package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class PrimeCollabTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimeCollab.class);
        PrimeCollab primeCollab1 = new PrimeCollab();
        primeCollab1.setId(1L);
        PrimeCollab primeCollab2 = new PrimeCollab();
        primeCollab2.setId(primeCollab1.getId());
        assertThat(primeCollab1).isEqualTo(primeCollab2);
        primeCollab2.setId(2L);
        assertThat(primeCollab1).isNotEqualTo(primeCollab2);
        primeCollab1.setId(null);
        assertThat(primeCollab1).isNotEqualTo(primeCollab2);
    }
}
