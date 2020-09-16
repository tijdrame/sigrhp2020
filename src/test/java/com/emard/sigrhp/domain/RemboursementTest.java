package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class RemboursementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remboursement.class);
        Remboursement remboursement1 = new Remboursement();
        remboursement1.setId(1L);
        Remboursement remboursement2 = new Remboursement();
        remboursement2.setId(remboursement1.getId());
        assertThat(remboursement1).isEqualTo(remboursement2);
        remboursement2.setId(2L);
        assertThat(remboursement1).isNotEqualTo(remboursement2);
        remboursement1.setId(null);
        assertThat(remboursement1).isNotEqualTo(remboursement2);
    }
}
