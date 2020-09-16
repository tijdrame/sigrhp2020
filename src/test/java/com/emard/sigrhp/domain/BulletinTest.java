package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class BulletinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bulletin.class);
        Bulletin bulletin1 = new Bulletin();
        bulletin1.setId(1L);
        Bulletin bulletin2 = new Bulletin();
        bulletin2.setId(bulletin1.getId());
        assertThat(bulletin1).isEqualTo(bulletin2);
        bulletin2.setId(2L);
        assertThat(bulletin1).isNotEqualTo(bulletin2);
        bulletin1.setId(null);
        assertThat(bulletin1).isNotEqualTo(bulletin2);
    }
}
