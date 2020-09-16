package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class ExerciceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exercice.class);
        Exercice exercice1 = new Exercice();
        exercice1.setId(1L);
        Exercice exercice2 = new Exercice();
        exercice2.setId(exercice1.getId());
        assertThat(exercice1).isEqualTo(exercice2);
        exercice2.setId(2L);
        assertThat(exercice1).isNotEqualTo(exercice2);
        exercice1.setId(null);
        assertThat(exercice1).isNotEqualTo(exercice2);
    }
}
