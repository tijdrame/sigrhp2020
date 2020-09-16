package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class MembreFamilleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreFamille.class);
        MembreFamille membreFamille1 = new MembreFamille();
        membreFamille1.setId(1L);
        MembreFamille membreFamille2 = new MembreFamille();
        membreFamille2.setId(membreFamille1.getId());
        assertThat(membreFamille1).isEqualTo(membreFamille2);
        membreFamille2.setId(2L);
        assertThat(membreFamille1).isNotEqualTo(membreFamille2);
        membreFamille1.setId(null);
        assertThat(membreFamille1).isNotEqualTo(membreFamille2);
    }
}
