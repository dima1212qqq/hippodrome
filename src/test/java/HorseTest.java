import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    Horse mock;

    @BeforeEach
    void setUp() {
        mock = Mockito.mock(Horse.class);
    }

    private Horse horse;

    @Test
    void firstConstructorNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 10, 100);
        });
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "    "})
    void firstConstructorEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, 10, 100);
        });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void secondConstructorNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Horse", -1, 100);
        });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void thirdConstructorNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Horse", 1, -1);
        });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        mock = new Horse("Horse", 10);
        assertEquals("Horse", mock.getName());
    }

    @Test
    void getSpeed() {
        mock = new Horse("Horse", 10);
        assertEquals(10, mock.getSpeed());
    }

    @Test
    void getDistance() {
        mock = new Horse("Horse", 10, 10);
        assertEquals(10, mock.getDistance());
    }

    @Test
    void move() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Horse", 10, 10);
            horse.move();
            horseMockedStatic.verify(()->Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.3,0.5,0.7})
    void moveTestFormula(Double d){
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)){
            horseMockedStatic.when(()->Horse.getRandomDouble(0.2,0.9)).thenReturn(d);
            mock = new Horse("Horse",0.1,0.10);
            mock.move();
            double idealValueDistance = 0.1+0.1*d;
            double result = mock.getDistance();
            assertEquals(idealValueDistance,result);
        }
    }
}