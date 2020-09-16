package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class NationaliteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nationalite.class);
        Nationalite nationalite1 = new Nationalite();
        nationalite1.setId(1L);
        Nationalite nationalite2 = new Nationalite();
        nationalite2.setId(nationalite1.getId());
        assertThat(nationalite1).isEqualTo(nationalite2);
        nationalite2.setId(2L);
        assertThat(nationalite1).isNotEqualTo(nationalite2);
        nationalite1.setId(null);
        assertThat(nationalite1).isNotEqualTo(nationalite2);
    }
}
