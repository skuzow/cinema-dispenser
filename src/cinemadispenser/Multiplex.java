package cinemadispenser;

import cinemadispenser.operations.MainMenu;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

/**
 * Multiplex class
 */
public class Multiplex {

    /**
     * Idioms String HashSet
     */
    private final HashSet<String> idiomHashSet = new HashSet<>();
    /**
     * Current Idiom working
     */
    private String idiom;

    /**
     * Starts all, and have main loop of the application
     * @throws IOException IO exception
     * @throws ClassNotFoundException Class not found
     */
    public void start() throws IOException, ClassNotFoundException {
        CinemaTicketDispenser dispenser = new CinemaTicketDispenser();
        UrjcBankServer bank = new UrjcBankServer();
        MainMenu menu = new MainMenu(dispenser, this);
        File idiomsDir = new File("./src/resources/idioms/");
        // searches for idioms and collect them into HashSet<String> idiomHashSet
        for (File idiomFile: Objects.requireNonNull(idiomsDir.listFiles())) {
            // cinemadispenser_idiom.properties -> idiom.properties
            final String headclear = idiomFile.toString().substring(idiomFile.toString().indexOf("_") + 1).trim();
            // idiom.properties -> idiom
            this.idiomHashSet.add(headclear.substring(0, headclear.length() - 11));
        }
        // main loop
        while (true) {
            // sets default language
            this.setIdiom("en");
            for (Operation operation: menu.getOperationList()) {
                if (Objects.equals(operation.getTitle(), "MainMenu")) {
                    operation.doOperation();
                }
            }
        }
    }

    /**
     * Sets idiom if exists in HashSet idiomHashSet
     * @param idiom String idiom
     */
    public void setIdiom(String idiom) {
        if (idiomHashSet.contains(idiom)) {
            this.idiom = idiom;
        }
    }

    /**
     * Gets HashSet idiomHashSet
     * @return HashSet idiomHashSet
     */
    public HashSet<String> getIdiomHashSet() {
        return idiomHashSet;
    }

    /**
     * Gets String idiom
     * @return String idiom
     */
    public String getIdiom() {
        return idiom;
    }

}
