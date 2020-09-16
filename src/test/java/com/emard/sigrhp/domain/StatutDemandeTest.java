package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class StatutDemandeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatutDemande.class);
        StatutDemande statutDemande1 = new StatutDemande();
        statutDemande1.setId(1L);
        StatutDemande statutDemande2 = new StatutDemande();
        statutDemande2.setId(statutDemande1.getId());
        assertThat(statutDemande1).isEqualTo(statutDemande2);
        statutDemande2.setId(2L);
        assertThat(statutDemande1).isNotEqualTo(statutDemande2);
        statutDemande1.setId(null);
        assertThat(statutDemande1).isNotEqualTo(statutDemande2);
    }
}
