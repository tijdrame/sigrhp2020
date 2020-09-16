package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class AvantageCollabTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvantageCollab.class);
        AvantageCollab avantageCollab1 = new AvantageCollab();
        avantageCollab1.setId(1L);
        AvantageCollab avantageCollab2 = new AvantageCollab();
        avantageCollab2.setId(avantageCollab1.getId());
        assertThat(avantageCollab1).isEqualTo(avantageCollab2);
        avantageCollab2.setId(2L);
        assertThat(avantageCollab1).isNotEqualTo(avantageCollab2);
        avantageCollab1.setId(null);
        assertThat(avantageCollab1).isNotEqualTo(avantageCollab2);
    }
}
