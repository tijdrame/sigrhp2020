package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class TypeRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRelation.class);
        TypeRelation typeRelation1 = new TypeRelation();
        typeRelation1.setId(1L);
        TypeRelation typeRelation2 = new TypeRelation();
        typeRelation2.setId(typeRelation1.getId());
        assertThat(typeRelation1).isEqualTo(typeRelation2);
        typeRelation2.setId(2L);
        assertThat(typeRelation1).isNotEqualTo(typeRelation2);
        typeRelation1.setId(null);
        assertThat(typeRelation1).isNotEqualTo(typeRelation2);
    }
}
