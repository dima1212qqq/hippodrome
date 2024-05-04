import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class HippodromeTest {
    List<Horse> horses = new ArrayList<>();
    Hippodrome mock;
    Random random = new Random();

    @BeforeEach
    void setUp() {

        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, random.nextDouble(), random.nextDouble()));
        }
        horses.add(new Horse("q",Double.MAX_VALUE,Double.MAX_VALUE));
        mock = Mockito.mock(Hippodrome.class);
        mock = new Hippodrome(horses);
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void firstConstructorNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void firstConstructorNullList() {
        horses.clear();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(horses);
        });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> horseList = mock.getHorses();
        Assertions.assertEquals(horseList, horses);

    }

    @Test
    void move() {
        List<Horse> horsesMock = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horsesMock.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horsesMock);
        hippodrome.move();
        for (Horse horseMock : horsesMock) {
            Mockito.verify(horseMock).move();
        }
    }

    @Test
    void getWinner() {
        double bigDouble = horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get().getDistance();
        Assertions.assertEquals(Double.MAX_VALUE,bigDouble);
    }
}