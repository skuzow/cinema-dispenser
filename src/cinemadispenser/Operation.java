package cinemadispenser;

import sienens.CinemaTicketDispenser;

/**
 * Operation abstract class
 */
public abstract class Operation {

    /**
     * CinemaTicketDispenser dispenser
     */
    private final CinemaTicketDispenser dispenser;
    /**
     * Multiplex multiplex
     */
    private final Multiplex multiplex;

    /**
     * Operation builder
     * @param dispenser CinemaTicketDispenser dispenser
     * @param multiplex Multiplex multiplex
     */
    public Operation(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        this.dispenser = dispenser;
        this.multiplex = multiplex;
    }

    /**
     * Does the operation needed
     */
    public abstract void doOperation();

    /**
     * Gets operation title
     * @return String title
     */
    public abstract String getTitle();

    /**
     * Gets CinemaTicketDispenser dispenser
     * @return CinemaTicketDispenser dispenser
     */
    public CinemaTicketDispenser getDispenser() {
        return dispenser;
    }

    /**
     * Gets Multiplex multiplex
     * @return Multiplex multiplex
     */
    public Multiplex getMultiplex() {
        return multiplex;
    }

}
