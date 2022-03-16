package pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.domain;

import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.Visitor;
import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.DomainEntity;

import java.util.Set;
import javax.persistence.*;

@Entity
public class SamePercentage implements DomainEntity {

  @OneToMany	
  private Set<WeeklyScore> samePercentageWeeklyScores;

  @OneToOne
	private WeeklyScore weeklyScore;	

  public SamePercentage() {}
	
  public SamePercentage(WeeklyScore _weeklyScore) {
		this.weeklyScore = _weeklyScore;
  }

	public void setWeeklyScores(Set<WeeklyScore> weeklyScores){
		this.samePercentageWeeklyScores = weeklyScores;
	}
  
	public Set<WeeklyScore> getWeeklyScores() {
		return this.samePercentageWeeklyScores;
	}

  public void accept(Visitor visitor) {
  }
}

