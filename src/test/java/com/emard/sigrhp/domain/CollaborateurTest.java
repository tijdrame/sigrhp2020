package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class CollaborateurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaborateur.class);
        Collaborateur collaborateur1 = new Collaborateur();
        collaborateur1.setId(1L);
        Collaborateur collaborateur2 = new Collaborateur();
        collaborateur2.setId(collaborateur1.getId());
        assertThat(collaborateur1).isEqualTo(collaborateur2);
        collaborateur2.setId(2L);
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);
        collaborateur1.setId(null);
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);
    }
}
