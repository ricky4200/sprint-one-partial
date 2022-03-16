import pt.ulisboa.tecnico.socialsoftware.tutor.impexp.domain.DomainEntity;
import java.util.List;
import javax.persistence.*;

@Entity
public class SamePercentage implements DomainEntity {

  @OneToMany
  private List<WeeklyScore> samePercentageWeeklyScores;

  @OneToOne
	private WeeklyScore weeklyScore;	

  public SamePercentage() {}
	
  public SamePercentage(WeeklyScore _weeklyScore) {
		this.weeklyScore = _weeklyScore;
  }

	public void setWeeklyScores(List<WeeklyScore> weeklyScores){
		this.samePercentageWeeklyScores = weeklyScores;
	}
  
	public List<WeeklyScore> getWeeklyScores() {
		return this.samePercentageWeeklyScores;
	}

}

