package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class RegimeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regime.class);
        Regime regime1 = new Regime();
        regime1.setId(1L);
        Regime regime2 = new Regime();
        regime2.setId(regime1.getId());
        assertThat(regime1).isEqualTo(regime2);
        regime2.setId(2L);
        assertThat(regime1).isNotEqualTo(regime2);
        regime1.setId(null);
        assertThat(regime1).isNotEqualTo(regime2);
    }
}
