
import com.pi4j.io.gpio.*;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Fredrik Mäkilä on 15-05-21.
 */
public class Raspi {

    public static void main(String[] args) throws InterruptedException {
        final AtomicBoolean turnOff = new AtomicBoolean();

        GpioController controller = GpioFactory.getInstance();
        GpioPinDigitalOutput lightSensor = controller.provisionDigitalOutputPin(
                RaspiPin.GPIO_01, "GPIO_01");

        final GpioPinDigitalOutput green = controller.provisionDigitalOutputPin(RaspiPin.GPIO_04, "green", PinState.HIGH);
        final GpioPinDigitalOutput yellow = controller.provisionDigitalOutputPin(RaspiPin.GPIO_05, "yellow", PinState.HIGH);
        final GpioPinDigitalOutput red = controller.provisionDigitalOutputPin(RaspiPin.GPIO_06, "red", PinState.HIGH);

        try {
            Runnable runner = new Runnable() {
                @Override
                public void run() {
                    while (true) {

                        synchronized (turnOff) {
                            if(turnOff.get()) {
                                green.low();
                                yellow.low();
                                red.low();
                            } else {
                                int value = 0;
                                lightSensor.export(PinMode.DIGITAL_OUTPUT);
                                lightSensor.setState(PinState.LOW);
                                lightSensor.export(PinMode.DIGITAL_INPUT);

                                while (lightSensor.getState() == PinState.LOW) {
                                    value++;
                                }

                                if (value > 10) {
                                    green.high();
                                } else {
                                    green.low();
                                }

                                if (value > 5) {
                                    yellow.high();
                                } else {
                                    yellow.low();
                                }

                                if (value > 0) {
                                    red.high();
                                } else {
                                    red.low();
                                }
                            }
                        }

                    }

                }
            };

            Thread worker = new Thread(runner);
            worker.setDaemon(true);
            worker.start();


            System.out.print("Enter something:");

            Scanner in = new Scanner(System.in);
            while (!in.hasNext()) {}

            System.out.println("Bye!");

            turnOff.set(true);
            worker.interrupt();

        } finally {
            red.low();
            green.low();
            yellow.low();
        }
    }

}
