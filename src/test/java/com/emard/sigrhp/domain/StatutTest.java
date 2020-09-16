package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class StatutTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statut.class);
        Statut statut1 = new Statut();
        statut1.setId(1L);
        Statut statut2 = new Statut();
        statut2.setId(statut1.getId());
        assertThat(statut1).isEqualTo(statut2);
        statut2.setId(2L);
        assertThat(statut1).isNotEqualTo(statut2);
        statut1.setId(null);
        assertThat(statut1).isNotEqualTo(statut2);
    }
}
