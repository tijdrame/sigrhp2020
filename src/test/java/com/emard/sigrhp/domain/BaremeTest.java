package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class BaremeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bareme.class);
        Bareme bareme1 = new Bareme();
        bareme1.setId(1L);
        Bareme bareme2 = new Bareme();
        bareme2.setId(bareme1.getId());
        assertThat(bareme1).isEqualTo(bareme2);
        bareme2.setId(2L);
        assertThat(bareme1).isNotEqualTo(bareme2);
        bareme1.setId(null);
        assertThat(bareme1).isNotEqualTo(bareme2);
    }
}
