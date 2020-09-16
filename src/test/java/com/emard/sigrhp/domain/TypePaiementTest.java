package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class TypePaiementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePaiement.class);
        TypePaiement typePaiement1 = new TypePaiement();
        typePaiement1.setId(1L);
        TypePaiement typePaiement2 = new TypePaiement();
        typePaiement2.setId(typePaiement1.getId());
        assertThat(typePaiement1).isEqualTo(typePaiement2);
        typePaiement2.setId(2L);
        assertThat(typePaiement1).isNotEqualTo(typePaiement2);
        typePaiement1.setId(null);
        assertThat(typePaiement1).isNotEqualTo(typePaiement2);
    }
}
