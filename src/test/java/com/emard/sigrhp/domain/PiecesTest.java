package com.emard.sigrhp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.sigrhp.web.rest.TestUtil;

public class PiecesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pieces.class);
        Pieces pieces1 = new Pieces();
        pieces1.setId(1L);
        Pieces pieces2 = new Pieces();
        pieces2.setId(pieces1.getId());
        assertThat(pieces1).isEqualTo(pieces2);
        pieces2.setId(2L);
        assertThat(pieces1).isNotEqualTo(pieces2);
        pieces1.setId(null);
        assertThat(pieces1).isNotEqualTo(pieces2);
    }
}
