package cinemadispenser.operations;

import cinemadispenser.Multiplex;
import cinemadispenser.Operation;
import cinemadispenser.state.MultiplexState;
import sienens.CinemaTicketDispenser;

import java.time.temporal.ChronoUnit;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * MovieTicketSale class extends Operation
 */
public class MovieTicketSale extends Operation {

    /**
     * MultiplexState state
     */
    private MultiplexState state;

    /**
     * MovieTicketSale builder
     * @param dispenser CinemaTicketDispenser dispenser
     * @param multiplex Multiplex multiplex
     * @throws IOException IO exception
     * @throws ClassNotFoundException Class not found
     */
    public MovieTicketSale(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, ClassNotFoundException {
        super(dispenser, multiplex);
        String serializableDirectory = "./src/resources/serializable";
        String serializablePath = serializableDirectory + "/state.bin";
        File directory = new File(serializableDirectory);
        // creates /serializable directory if it doesn't exist
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("No state directory so, created successfully!");
            }
        }
        File file = new File(serializablePath);
        if (file.exists()) {
            LocalDateTime now = LocalDateTime.now();
            // state.bin FileTime
            BasicFileAttributes attr = Files.readAttributes(Path.of(serializablePath), BasicFileAttributes.class);
            // converts FileTime into LocalDateTime
            LocalDateTime convertedFileTime = LocalDateTime.ofInstant(attr.lastModifiedTime().toInstant(), ZoneId.systemDefault());
            // if state.bin exists & is not created in the same day, new MultiplexState & it creates again
            if (ChronoUnit.DAYS.between(convertedFileTime, now) != 0) {
                generateState(serializablePath);
                System.out.println("Old State so, new state generated and serialized successfully!");
            } else {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializablePath));
                state = (MultiplexState) in.readObject();
                System.out.println("New state created from state.bin successfully!");
            }
        } else {
            generateState(serializablePath);
            System.out.println("No state.bin file so, New state generated and serialized successfully!");
        }
    }

    /**
     * Generates state & state.bin
     * @param serializablePath String serializablePath
     * @throws IOException if something fails inside
     */
    private void generateState(String serializablePath) throws IOException {
        state = new MultiplexState();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializablePath));
        out.writeObject(state);
        out.flush();
        out.close();
    }

    /**
     * Does the operation needed in this case
     */
    @Override
    public void doOperation() {

    }

    /**
     * Gets the proper title in this case
     * @return String title
     */
    @Override
    public String getTitle() {
        return "MovieTicketSale";
    }

}
