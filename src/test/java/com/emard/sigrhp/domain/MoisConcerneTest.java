package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class MoisConcerneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoisConcerne.class);
        MoisConcerne moisConcerne1 = new MoisConcerne();
        moisConcerne1.setId(1L);
        MoisConcerne moisConcerne2 = new MoisConcerne();
        moisConcerne2.setId(moisConcerne1.getId());
        assertThat(moisConcerne1).isEqualTo(moisConcerne2);
        moisConcerne2.setId(2L);
        assertThat(moisConcerne1).isNotEqualTo(moisConcerne2);
        moisConcerne1.setId(null);
        assertThat(moisConcerne1).isNotEqualTo(moisConcerne2);
    }
}
