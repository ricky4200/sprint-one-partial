package pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuestionAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuizAnswer;
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.DomainEntity;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question;
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.QuizQuestion;
import pt.ulisboa.tecnico.socialsoftware.tutor.utils.DateHandler;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage.WEEKLY_SCORE_ALREADY_CREATED;

@Entity
public class WeeklyScore implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int numberAnswered;

    private int uniquelyAnswered;

    private int percentageCorrect;

    private LocalDate week;

    @OneToMany
    private SamePercentage samePercentageWeeklys;

    @ManyToOne
    private Dashboard dashboard;

    public WeeklyScore() {}

    public WeeklyScore(Dashboard dashboard, LocalDate week) {
        setWeek(week);
        setDashboard(dashboard);
        this.samePercentageWeeklys = new SamePercentage(this);
        udpateSamePercentage(true);
    }


    public Integer getId() { return id; }

    public int getNumberAnswered() { return numberAnswered; }

    public void setNumberAnswered(int numberAnswered) {
        this.numberAnswered = numberAnswered;
    }

    public int getUniquelyAnswered() { return uniquelyAnswered; }

    public void setUniquelyAnswered(int uniquelyAnswered) {
        this.uniquelyAnswered = uniquelyAnswered;
    }

    public int getPercentageCorrect() { return percentageCorrect; }

    public void setPercentageCorrect(int percentageCorrect) {
        this.percentageCorrect = percentageCorrect;
    }

    public LocalDate getWeek() { return week; }

    public void setWeek(LocalDate week) {
        this.week = week;
    }

    public Dashboard getDashboard() { return dashboard; }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
        this.dashboard.addWeeklyScore(this);
    }

    public void accept(Visitor visitor) {
    }

    public void remove() {

        this.dashboard.getWeeklyScores().remove(this);
        List<WeeklyScore> weeklyScoresSamePercentage = this.samePercentage.getWeeklyScores();
        for (WeeklyScore ws: weeklyScoresSamePercentage) {
            ws.updateSamePercentage(false);            
        }

        this.samePercentage = null;
        this.dashboard = null;

    }


    public void updateSamePercentage(boolean updateOthers) {
        List<WeeklyScore> weeklyScores = dashboard.getWeeklyScores();
        List<WeeklyScore> weeklyScoresSamePercentage = new List<WeeklyScore>();
        
        for (WeeklyScore ws : weeklyScores) {
            if (ws.getPercentageCorrect() == this.percentageCorrect && ws != this) {
                weeklyScoresSamePercentage.add(ws);

                // make others update their SamePercentage's list
                if (updateOthers) {
                  // false to not start an infinite update loop
                  ws.updateSamePercentage(false);
                }
            }
        }
        this.samePercentage.setWeeklyScores(weeklyScoresSamePercentage);
    }

    @Override
    public String toString() {
        return "WeeklyScore{" +
                "id=" + getId() +
                ", numberAnswered=" + numberAnswered +
                ", uniquelyAnswered=" + uniquelyAnswered +
                ", percentageCorrect=" + percentageCorrect +
                ", week=" + getWeek() +
                "}";
    }
}
