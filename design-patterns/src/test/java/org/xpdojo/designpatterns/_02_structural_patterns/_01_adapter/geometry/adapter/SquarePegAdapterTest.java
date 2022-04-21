package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.geometry.adapter;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.geometry.round.RoundHole;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.geometry.round.RoundPeg;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.geometry.square.SquarePeg;

import static org.assertj.core.api.Assertions.assertThat;

class SquarePegAdapterTest {

    @Test
    void sut_round_peg() {
        RoundHole roundHole = new RoundHole(5);
        RoundPeg roundPeg = new RoundPeg(5);

        assertThat(roundHole.fits(roundPeg)).isTrue();
    }

    @Test
    void sut_square_peg_adapter() {
        RoundHole roundHole = new RoundHole(5);

        SquarePeg smallSquarePeg = new SquarePeg(2);
        SquarePeg largeSquarePeg = new SquarePeg(20);

        SquarePegAdapter smallSquarePegAdapter = new SquarePegAdapter(smallSquarePeg);
        SquarePegAdapter largeSquarePegAdapter = new SquarePegAdapter(largeSquarePeg);

        assertThat(roundHole.fits(smallSquarePegAdapter)).isTrue();
        assertThat(roundHole.fits(largeSquarePegAdapter)).isFalse();
    }

}
