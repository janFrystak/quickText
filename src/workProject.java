import java.math.BigDecimal;
import java.time.LocalDate;

public class workProject {
    private String name;
    private int count;
    private boolean done;
    private LocalDate startDate;
    private BigDecimal funds;

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }

    private int rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public workProject(String name, int count, boolean done, LocalDate startDate, int rating, BigDecimal funds) {
        this.name = name;
        this.count = count;
        this.done = done;
        this.startDate = startDate;
        this.rating = rating;
        this.funds = funds;
    }
}
