package cinemadispenser.state;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Theater class implements Serializable
 */
public class Theater implements Serializable {

    /**
     * Theater number
     */
    private int number;
    /**
     * Theater price
     */
    private int price;
    /**
     * Theater max rows
     */
    private int maxrows = 1;
    /**
     * Theater max columns
     */
    private int maxcols = 1;
    /**
     * Theater Seat Set
     */
    private final Set<Seat> seatSet = new HashSet<>();
    /**
     * Theater Film List
     */
    private final List<Film> filmList = new ArrayList<>();
    /**
     * Theater Session List
     */
    private final List<Session> sessionList = new ArrayList<>();

    /**
     * Theater builder
     * @param theaterFile File theaterFile
     * @param moviesFiles File moviesFiles
     * @throws FileNotFoundException if theaterFile or moviesFiles doesn't exist
     */
    public Theater(File theaterFile, File[] moviesFiles) throws FileNotFoundException {
        this.setNumber(theaterFile);
        this.setPrice();
        this.generateSeatSet(theaterFile);
        this.generateFilmSessionList(moviesFiles);
    }

    /**
     * Sets theater number, searching the corresponding one in the txt file
     * @param file File theaterFile
     */
    private void setNumber(File file) {
        for (char ch: file.toString().toCharArray()) {
            if (Character.isDigit(ch)) {
                this.number = Character.getNumericValue(ch);
            }
        }
    }

    /**
     * Sets random price between minprice & maxprice to price
     */
    private void setPrice() {
        int minprice = 10;
        int maxprice = 15;
        this.price = (int) (Math.random() * (maxprice - minprice)) + minprice;
    }

    /**
     * Creates Seat Set with theaterFile directory, also maxrows & maxcols
     * @param file File theaterFile
     * @throws FileNotFoundException if theaterFile doesn't exist
     */
    private void generateSeatSet(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(file));
        int row = 1;
        int col = 1;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            for (char ch: line.toCharArray()) {
                if (ch == '*') {
                    this.seatSet.add(new Seat(row, col));
                }
                col += 1;
            }
            if (this.maxcols < col - 1) {
                this.maxcols = col;
            }
            if (this.maxrows < row ) {
                this.maxrows = row;
            }
            col = 1;
            row += 1;
        }
    }

    /**
     * Creates corresponding films to Theater & adds them into filmList,
     * same with sessions with sessionList
     * @param files File moviesFiles
     * @throws FileNotFoundException if moviesFiles doesn't exist
     */
    private void generateFilmSessionList (File[] files) throws FileNotFoundException {
        for (File file: files) {
            Scanner sc = new Scanner(new FileReader(file));
            // while for searching theater films
            boolean found = false;
            // while for getting Theatre Films & Sessions
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.startsWith("Theatre: ")) {
                    for (char ch : line.toCharArray()) {
                        if (Character.isDigit(ch) && number == Character.getNumericValue(ch)) {
                            this.filmList.add(new Film(file));
                            found = true;
                        }
                    }
                } else if (line.startsWith("Sessions: ") && found) {
                    String sessions = line.substring(line.indexOf(":") + 1).trim();
                    // while for getting sessions from sessions string & converts them to LocalTime format to create new Session
                    while (!sessions.isEmpty()) {
                        this.sessionList.add(new Session(LocalTime.parse(sessions.substring(0, 5), DateTimeFormatter.ofPattern("HH:mm")), this.seatSet));
                        sessions = sessions.substring(sessions.indexOf(":") + 3).trim();
                    }
                }
            }
        }
    }

    /**
     * Gets int number
     * @return int number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Gets int price
     * @return int price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Gets int maxrows
     * @return int maxrows
     */
    public int getMaxRows() {
        return this.maxrows;
    }

    /**
     * Gets max columns
     * @return int maxcols
     */
    public int getMaxCols() {
        return this.maxcols;
    }

    /**
     * Gets Set seatSet
     * @return Set seatSet
     */
    public Set<Seat> getSeatSeat() {
        return this.seatSet;
    }

    /**
     * Gets List filmList
     * @return List filmList
     */
    public List<Film> getFilmList() {
        return this.filmList;
    }

    /**
     * Gets List sessionList
     * @return List sessionList
     */
    public List<Session> getSessionList() {
        return this.sessionList;
    }

}
