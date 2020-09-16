package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class TypeAbsenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAbsence.class);
        TypeAbsence typeAbsence1 = new TypeAbsence();
        typeAbsence1.setId(1L);
        TypeAbsence typeAbsence2 = new TypeAbsence();
        typeAbsence2.setId(typeAbsence1.getId());
        assertThat(typeAbsence1).isEqualTo(typeAbsence2);
        typeAbsence2.setId(2L);
        assertThat(typeAbsence1).isNotEqualTo(typeAbsence2);
        typeAbsence1.setId(null);
        assertThat(typeAbsence1).isNotEqualTo(typeAbsence2);
    }
}
