package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class TypeContratTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeContrat.class);
        TypeContrat typeContrat1 = new TypeContrat();
        typeContrat1.setId(1L);
        TypeContrat typeContrat2 = new TypeContrat();
        typeContrat2.setId(typeContrat1.getId());
        assertThat(typeContrat1).isEqualTo(typeContrat2);
        typeContrat2.setId(2L);
        assertThat(typeContrat1).isNotEqualTo(typeContrat2);
        typeContrat1.setId(null);
        assertThat(typeContrat1).isNotEqualTo(typeContrat2);
    }
}
