package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class MotifTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motif.class);
        Motif motif1 = new Motif();
        motif1.setId(1L);
        Motif motif2 = new Motif();
        motif2.setId(motif1.getId());
        assertThat(motif1).isEqualTo(motif2);
        motif2.setId(2L);
        assertThat(motif1).isNotEqualTo(motif2);
        motif1.setId(null);
        assertThat(motif1).isNotEqualTo(motif2);
    }
}
