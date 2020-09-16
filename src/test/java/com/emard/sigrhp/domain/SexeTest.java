package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class SexeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sexe.class);
        Sexe sexe1 = new Sexe();
        sexe1.setId(1L);
        Sexe sexe2 = new Sexe();
        sexe2.setId(sexe1.getId());
        assertThat(sexe1).isEqualTo(sexe2);
        sexe2.setId(2L);
        assertThat(sexe1).isNotEqualTo(sexe2);
        sexe1.setId(null);
        assertThat(sexe1).isNotEqualTo(sexe2);
    }
}
