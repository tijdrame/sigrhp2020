package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class ConventionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Convention.class);
        Convention convention1 = new Convention();
        convention1.setId(1L);
        Convention convention2 = new Convention();
        convention2.setId(convention1.getId());
        assertThat(convention1).isEqualTo(convention2);
        convention2.setId(2L);
        assertThat(convention1).isNotEqualTo(convention2);
        convention1.setId(null);
        assertThat(convention1).isNotEqualTo(convention2);
    }
}
