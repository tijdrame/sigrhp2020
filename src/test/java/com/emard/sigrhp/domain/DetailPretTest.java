package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class DetailPretTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailPret.class);
        DetailPret detailPret1 = new DetailPret();
        detailPret1.setId(1L);
        DetailPret detailPret2 = new DetailPret();
        detailPret2.setId(detailPret1.getId());
        assertThat(detailPret1).isEqualTo(detailPret2);
        detailPret2.setId(2L);
        assertThat(detailPret1).isNotEqualTo(detailPret2);
        detailPret1.setId(null);
        assertThat(detailPret1).isNotEqualTo(detailPret2);
    }
}
